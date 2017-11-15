package cn.itsource.aigou.web.controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itsource.aigou.core.consts.GlobalSettingNames;
import cn.itsource.aigou.core.domain.Sso;
import cn.itsource.aigou.core.utils.GlobalSetting;
import cn.itsource.aigou.core.utils.Ret;
import cn.itsource.aigou.facade.CommonService;
import cn.itsource.aigou.facade.UserCenterService;

@Controller
public class SsoServerController {

	@Reference
	private UserCenterService userCenterService;

	@Reference
	private CommonService commonService;

	/**
	 * SSO服务器统一登录接口 from 登录客户端来源接口地址（回传st）
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/login")
	public String login(String from,String to, Integer relogin, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (StringUtils.isBlank(from)) {
			response.sendRedirect(GlobalSetting.get(GlobalSettingNames.URL_WWW));
			return null;
		}
		
		if(StringUtils.isBlank(to)){
			to= "";
		}

		// 获取tgc
		Cookie[] cookies = request.getCookies();
		String tgc = "";
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("tgc")) {
					tgc = cookie.getValue();
					// 强制重新登录
					if (null != relogin && 1 == relogin) {
						// 删除tgc
						commonService.deleteSsoTGC(tgc);
						// 重置tgc并删除cookie
						tgc = "";
						cookie.setMaxAge(0);
						cookie.setPath("/");
						response.addCookie(cookie);
					}
				}
			}
		}

		// 存在tgc
		if (StringUtils.isNotBlank(tgc)) {
			boolean validateSsoTGC = commonService.validateSsoTGC(tgc);
			if (validateSsoTGC) {// tgc有效
				// 获取票据
				String st = commonService.getSsoST(tgc);
				// 刷新tgc过期时间
				commonService.refreshTGCExpires(tgc);
				// 返回票据给调用者
				response.sendRedirect(from + "?st=" + st + "&to="+URLEncoder.encode(to,"utf-8"));
				return null;
			}
		}
		
		
		// 需要登录
		response.sendRedirect("/login.html?wwwurl=" + GlobalSetting.get(GlobalSettingNames.URL_WWW) + "&from=" + from+"&to="+URLEncoder.encode(to,"utf-8"));
		return null;
	}

	/**
	 * sso登录表单登录处理
	 * 
	 * @param username
	 * @param password
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/login/passport/in")
	@ResponseBody
	public Ret loginByPassport(String username, String password, HttpServletRequest request,
			HttpServletResponse response) {
		Ret ret = userCenterService.login(username, password);
		if (ret.isSuccess()) {// 登录成功
			// 获取登录sso用户
			Sso sso = userCenterService.getSsoUser(Long.parseLong(ret.getData().toString()));
			// 写入sso登录信息到redis
			commonService.setRedisSsoId(sso.getId());
			// 写入SSO单点登录TGC到浏览器cookie
			String tgc = commonService.getSsoTGC(sso.getId().toString());
			Cookie tgcCookie = new Cookie("tgc", tgc);
			tgcCookie.setPath("/");// 所有访问路径的目录都可以访问cookie
			response.addCookie(tgcCookie);

			// 返回st
			String st = commonService.getSsoST(tgc);
			ret.setData(st);
		}
		return ret;
	}

	/**
	 * sso注销
	 * 
	 * @param back
	 *            注销后跳转地址
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/logout")
	public String logOut(String back, HttpServletRequest request, HttpServletResponse response) {
		String redirectUrl = back;
		if (StringUtils.isBlank(back)) {
			redirectUrl = "/";
		}

		// 获取tgc，并注销tgc
		Cookie[] cookies = request.getCookies();
		String tgc = "";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("tgc")) {
				tgc = cookie.getValue();
				// 删除tgc
				commonService.deleteSsoTGC(tgc);
				
				// 删除cookie
				cookie.setMaxAge(0);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
		
		return "redirect:" + redirectUrl;
	}
}
