package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.VipBill;
import cn.itsource.aigou.core.mapper.VipBillMapper;
import cn.itsource.aigou.service.IVipBillService;
@Service
public class VipBillServiceImpl extends BaseServiceImpl<VipBill> implements IVipBillService{
	@Autowired
	private VipBillMapper mapper;
	
	@Override
	protected BaseMapper<VipBill> getMapper() {
		return mapper;
	}
}
