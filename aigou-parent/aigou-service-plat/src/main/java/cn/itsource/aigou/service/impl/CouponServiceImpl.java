package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.Coupon;
import cn.itsource.aigou.core.mapper.CouponMapper;
import cn.itsource.aigou.service.ICouponService;
@Service
public class CouponServiceImpl extends BaseServiceImpl<Coupon> implements ICouponService{
	@Autowired
	private CouponMapper mapper;
	
	@Override
	protected BaseMapper<Coupon> getMapper() {
		return mapper;
	}
}
