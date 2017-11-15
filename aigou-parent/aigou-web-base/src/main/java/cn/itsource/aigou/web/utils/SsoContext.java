package cn.itsource.aigou.web.utils;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.itsource.aigou.core.domain.Sso;

/**
 * 用户上下文对象，获取当前登录的用户
 * @author nixianhua
 */
public class SsoContext {
	public static final String SSO_USER_IN_SESSION = "sso";
	private static HttpServletRequest getRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public static Sso getSso(){
		return (Sso) getRequest().getSession().getAttribute(SSO_USER_IN_SESSION);
	}
	
	public static Long getSsoId() {
		return SsoContext.getSso().getId();
	}
	
	public static void setSso(Sso sso){
		getRequest().getSession().setAttribute(SSO_USER_IN_SESSION,sso);
	}
	
	public static void logOut(){
		getRequest().getSession().invalidate();
	}
}
