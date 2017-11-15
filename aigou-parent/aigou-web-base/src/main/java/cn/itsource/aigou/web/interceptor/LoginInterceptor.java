package cn.itsource.aigou.web.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

import cn.itsource.aigou.core.consts.GlobalSettingNames;
import cn.itsource.aigou.core.consts.ICodes;
import cn.itsource.aigou.core.domain.Sso;
import cn.itsource.aigou.core.utils.GlobalSetting;
import cn.itsource.aigou.core.utils.Ret;
import cn.itsource.aigou.web.utils.SsoContext;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//获取请求地址
		StringBuffer requestURL = request.getRequestURL();
		String to = request.getHeader("Referer");
		if(StringUtils.isBlank(to)){
			to = "";
		}
		
		Sso sso = SsoContext.getSso();
		boolean isLogin = sso != null;
		if(isLogin){//如果有session
			String ssoId = RedisSsoUtils.getSsoId(sso.getId());
			if(StringUtils.isBlank(ssoId)){//中央session已过期
				SsoContext.logOut();
				isLogin = false;
			}else{//中央session未过期
				RedisSsoUtils.refreshRedisSsoId(sso.getId());
			}
		}
		
		if (!isLogin) {
			String header = request.getHeader("X-Requested-With");
			boolean isAjax = "XMLHttpRequest".equalsIgnoreCase(header);
			String clientLogin = "/"+GlobalSetting.get(GlobalSettingNames.URL_CLIENT_LOGIN);
			if (!isAjax) {
				to = requestURL.toString();
				clientLogin+="?to="+URLEncoder.encode(to,"utf-8");
				response.sendRedirect(clientLogin);
			} else {
				clientLogin+="?to="+URLEncoder.encode(to,"utf-8");
				Ret ret = Ret.me().setSuccess(false).setCode(ICodes.UNAUTHED).setData(clientLogin);
				String retJson = JSON.toJSONString(ret);
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().write(retJson);
			}
		}
		return isLogin;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}
}
