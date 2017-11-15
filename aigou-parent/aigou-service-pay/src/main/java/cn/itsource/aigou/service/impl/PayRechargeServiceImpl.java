package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.PayRecharge;
import cn.itsource.aigou.core.mapper.PayRechargeMapper;
import cn.itsource.aigou.service.IPayRechargeService;
@Service
public class PayRechargeServiceImpl extends BaseServiceImpl<PayRecharge> implements IPayRechargeService{
	@Autowired
	private PayRechargeMapper mapper;
	
	@Override
	protected BaseMapper<PayRecharge> getMapper() {
		return mapper;
	}
}
