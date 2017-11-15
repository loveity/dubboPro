package cn.itsource.aigou.service;

import cn.itsource.aigou.core.common.base.IBaseService;
import cn.itsource.aigou.core.domain.Sso;
import cn.itsource.aigou.core.utils.Ret;

public interface ISsoService extends IBaseService<Sso> {

	/**
	 * 通过手机号码获取会员
	 * @param phone
	 * @return
	 */
	Sso getSsoByPhone(String phone);

	/**
	 * 通过手机号码注册用户
	 * @param phone 手机号码
	 * @param password 密码
	 * @param smsCaptcha 手机验证码
	 * @return
	 */
	Ret regUserByPhone(String phone, String password, String smsCaptcha);

	/**
	 * 通过用户名和密码登录
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 */
	Ret login(String username, String password);
	
}
