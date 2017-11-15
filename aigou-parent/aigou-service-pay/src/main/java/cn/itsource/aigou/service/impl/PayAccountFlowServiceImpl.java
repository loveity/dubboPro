package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.PayAccountFlow;
import cn.itsource.aigou.core.mapper.PayAccountFlowMapper;
import cn.itsource.aigou.service.IPayAccountFlowService;
@Service
public class PayAccountFlowServiceImpl extends BaseServiceImpl<PayAccountFlow> implements IPayAccountFlowService{
	@Autowired
	private PayAccountFlowMapper mapper;
	
	@Override
	protected BaseMapper<PayAccountFlow> getMapper() {
		return mapper;
	}
}
