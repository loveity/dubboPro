package cn.itsource.aigou.service;

import cn.itsource.aigou.core.common.base.IBaseService;
import cn.itsource.aigou.core.domain.PayAccount;
import cn.itsource.aigou.core.domain.Sso;

public interface IPayAccountService extends IBaseService<PayAccount> {

	/**
	 * 床架用户账户信息
	 * @param sso
	 */
	void create(Sso sso);

	/**
	 * 通过用户ID获取用户账户
	 * @param ssoId
	 * @return
	 */
	PayAccount getBySso(Long ssoId);
	
}
