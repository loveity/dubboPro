package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.PayCredit;
import cn.itsource.aigou.core.mapper.PayCreditMapper;
import cn.itsource.aigou.service.IPayCreditService;
@Service
public class PayCreditServiceImpl extends BaseServiceImpl<PayCredit> implements IPayCreditService{
	@Autowired
	private PayCreditMapper mapper;
	
	@Override
	protected BaseMapper<PayCredit> getMapper() {
		return mapper;
	}
}
