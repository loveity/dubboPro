package cn.itsource.aigou.web.controller;

public interface IControllerHelper {
	/**
	 * 获取中央session中的ssoId
	 * @param ssoId
	 * @return
	 */
	String getSsoId(Long ssoId);
	/**
	 * 刷新中央session ssoId的过期时间
	 * @param ssoId
	 */
	void refreshRedisSsoId(Long ssoId);
}
