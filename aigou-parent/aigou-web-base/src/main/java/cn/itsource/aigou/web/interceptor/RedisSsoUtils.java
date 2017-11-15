package cn.itsource.aigou.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;

import cn.itsource.aigou.web.controller.ControllerHelper;

public class RedisSsoUtils {
	private static ControllerHelper controllerHelper;
	
	@Autowired
	public void setControllerHelper(ControllerHelper controllerHelper) {
		RedisSsoUtils.controllerHelper = controllerHelper;
		//System.out.println("RedisSsoUtils.controllerHelper="+RedisSsoUtils.controllerHelper);
	}
	
	/**获取中央缓存的ssoId*/
	public static String getSsoId(Long ssoId){
		return controllerHelper.getSsoId(ssoId);
	}

	/**刷新中央缓存的ssoId*/
	public static void refreshRedisSsoId(Long ssoId) {
		controllerHelper.refreshRedisSsoId(ssoId);
	}
	
}
