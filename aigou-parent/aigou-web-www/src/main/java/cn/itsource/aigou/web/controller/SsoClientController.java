package cn.itsource.aigou.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itsource.aigou.core.consts.GlobalSettingNames;
import cn.itsource.aigou.core.domain.Sso;
import cn.itsource.aigou.core.utils.GlobalSetting;
import cn.itsource.aigou.facade.CommonService;
import cn.itsource.aigou.facade.UserCenterService;
import cn.itsource.aigou.web.utils.SsoContext;

@Controller
@RequestMapping("/sso")
public class SsoClientController {
	@Reference
	private CommonService commonService;

	@Reference
	private UserCenterService userCenterService;

	/**
	 * 客户端的注册跳转
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/reg")
	public String reg(String to) throws UnsupportedEncodingException {
		String toLink = to;
		if(StringUtils.isBlank(to)){
			toLink = "";
		}
		String ssoRegUrl = GlobalSetting.get(GlobalSettingNames.URL_SSO_REG) + "?to="+URLEncoder.encode(toLink, "utf-8")+"&from="
				+ GlobalSetting.get(GlobalSettingNames.URL_WWW)
				+ GlobalSetting.get(GlobalSettingNames.URL_CLIENT_LOGIN);
		return "redirect:" + ssoRegUrl;
	}

	/**
	 * 商城系统 会员登录接口
	 * 
	 * @param st
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/login")
	public String login(String st,String to) throws IOException {
		String fromUrl = GlobalSetting.get(GlobalSettingNames.URL_WWW)
				+ GlobalSetting.get(GlobalSettingNames.URL_CLIENT_LOGIN);
		String redirectUrl = "";
		String toLink = to;
		if(StringUtils.isBlank(toLink)) {
			toLink= "";
		}
		if (StringUtils.isNotBlank(st)) {// 有sso访问票据
			String ssoId = commonService.validateSsoST(st);
			if (StringUtils.isNotBlank(ssoId)) {// 票据有效
				// 获取登录sso用户
				Sso sso = userCenterService.getSsoUser(Long.parseLong(ssoId));
				// 写入本地系统session
				SsoContext.setSso(sso);
				redirectUrl = "/";
				if(StringUtils.isNotBlank(toLink)){
					redirectUrl = toLink;
				}
			} else {// 票据无效，强制重新登录
				redirectUrl= GlobalSetting.get(GlobalSettingNames.URL_SSO_LOGIN) + "?relogin=1&from=" + fromUrl+"&to="+URLEncoder.encode(toLink,"utf-8");
			}

		} else {// 无票据，转到sso登录界面
			redirectUrl=GlobalSetting.get(GlobalSettingNames.URL_SSO_LOGIN) + "?from=" + fromUrl+"&to="+URLEncoder.encode(toLink,"utf-8");
		}
		return "redirect:"+redirectUrl;
	}
	
	@RequestMapping("/logout")
	public String logoOut(){
		SsoContext.logOut();
		String redirectUrl = GlobalSetting.get(GlobalSettingNames.URL_SSO_LOGOUT);
		redirectUrl+="?back="+GlobalSetting.get(GlobalSettingNames.URL_WWW);
		return "redirect:"+redirectUrl;
	}
}