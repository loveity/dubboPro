package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.OrderDetail;
import cn.itsource.aigou.core.mapper.OrderDetailMapper;
import cn.itsource.aigou.service.IOrderDetailService;
@Service
public class OrderDetailServiceImpl extends BaseServiceImpl<OrderDetail> implements IOrderDetailService{
	@Autowired
	private OrderDetailMapper mapper;
	
	@Override
	protected BaseMapper<OrderDetail> getMapper() {
		return mapper;
	}
}
