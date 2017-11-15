package cn.itsource.aigou.core.mapper;

import cn.itsource.aigou.core.domain.Sso;

public interface SsoMapper extends BaseMapper<Sso> {

	/**
	 * 通过手机号码获取用户
	 * @param phone
	 * @return
	 */
	Sso getSsoByPhone(String phone);
	
}