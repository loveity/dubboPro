package cn.itsource.aigou.core.mapper;

import cn.itsource.aigou.core.domain.PayAccount;

public interface PayAccountMapper extends BaseMapper<PayAccount> {

	/**
	 * 通过用户ID获取用户账户
	 * @param ssoId
	 * @return
	 */
	PayAccount getBySso(Long ssoId);
}