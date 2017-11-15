package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.PayInteraction;
import cn.itsource.aigou.core.mapper.PayInteractionMapper;
import cn.itsource.aigou.service.IPayInteractionService;
@Service
public class PayInteractionServiceImpl extends BaseServiceImpl<PayInteraction> implements IPayInteractionService{
	@Autowired
	private PayInteractionMapper mapper;
	
	@Override
	protected BaseMapper<PayInteraction> getMapper() {
		return mapper;
	}

	@Override
	public PayInteraction getByBillId(Long billId) {
		return mapper.getByBillId(billId);
	}
}
