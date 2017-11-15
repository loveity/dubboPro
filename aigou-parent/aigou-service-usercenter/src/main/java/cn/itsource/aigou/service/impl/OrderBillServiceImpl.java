package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.OrderBill;
import cn.itsource.aigou.core.mapper.OrderBillMapper;
import cn.itsource.aigou.service.IOrderBillService;
@Service
public class OrderBillServiceImpl extends BaseServiceImpl<OrderBill> implements IOrderBillService{
	@Autowired
	private OrderBillMapper mapper;
	
	@Override
	protected BaseMapper<OrderBill> getMapper() {
		return mapper;
	}
}
