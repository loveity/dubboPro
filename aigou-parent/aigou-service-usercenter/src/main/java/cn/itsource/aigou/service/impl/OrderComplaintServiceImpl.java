package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.OrderComplaint;
import cn.itsource.aigou.core.mapper.OrderComplaintMapper;
import cn.itsource.aigou.service.IOrderComplaintService;
@Service
public class OrderComplaintServiceImpl extends BaseServiceImpl<OrderComplaint> implements IOrderComplaintService{
	@Autowired
	private OrderComplaintMapper mapper;
	
	@Override
	protected BaseMapper<OrderComplaint> getMapper() {
		return mapper;
	}
}
