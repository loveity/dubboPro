package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.PayAccount;
import cn.itsource.aigou.core.domain.Sso;
import cn.itsource.aigou.core.mapper.PayAccountMapper;
import cn.itsource.aigou.service.IPayAccountService;
@Service
public class PayAccountServiceImpl extends BaseServiceImpl<PayAccount> implements IPayAccountService{
	@Autowired
	private PayAccountMapper mapper;
	
	@Override
	protected BaseMapper<PayAccount> getMapper() {
		return mapper;
	}

	@Override
	public void create(Sso sso) {
		PayAccount payAccount = new PayAccount();
		payAccount.setCouponCount(0);
		payAccount.setCreateTime(System.currentTimeMillis());
		payAccount.setUpdateTime(System.currentTimeMillis());
		payAccount.setCreditBanance(0);
		payAccount.setFrozenBalance(0);
		payAccount.setPayPassword(sso.getPassword());
		payAccount.setSsoId(sso.getId());
		payAccount.setUseableBalance(0);
		mapper.savePart(payAccount);
	}

	@Override
	public PayAccount getBySso(Long ssoId) {
		return mapper.getBySso(ssoId);
	}
}
