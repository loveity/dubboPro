package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.OrderReturn;
import cn.itsource.aigou.core.mapper.OrderReturnMapper;
import cn.itsource.aigou.service.IOrderReturnService;
@Service
public class OrderReturnServiceImpl extends BaseServiceImpl<OrderReturn> implements IOrderReturnService{
	@Autowired
	private OrderReturnMapper mapper;
	
	@Override
	protected BaseMapper<OrderReturn> getMapper() {
		return mapper;
	}
}
