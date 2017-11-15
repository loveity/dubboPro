package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.PayVipCoupon;
import cn.itsource.aigou.core.mapper.PayVipCouponMapper;
import cn.itsource.aigou.service.IPayVipCouponService;
@Service
public class PayVipCouponServiceImpl extends BaseServiceImpl<PayVipCoupon> implements IPayVipCouponService{
	@Autowired
	private PayVipCouponMapper mapper;
	
	@Override
	protected BaseMapper<PayVipCoupon> getMapper() {
		return mapper;
	}
}
