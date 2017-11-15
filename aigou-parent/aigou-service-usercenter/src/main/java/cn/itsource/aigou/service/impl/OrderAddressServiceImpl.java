package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.OrderAddress;
import cn.itsource.aigou.core.mapper.OrderAddressMapper;
import cn.itsource.aigou.service.IOrderAddressService;
@Service
public class OrderAddressServiceImpl extends BaseServiceImpl<OrderAddress> implements IOrderAddressService{
	@Autowired
	private OrderAddressMapper mapper;
	
	@Override
	protected BaseMapper<OrderAddress> getMapper() {
		return mapper;
	}
}
