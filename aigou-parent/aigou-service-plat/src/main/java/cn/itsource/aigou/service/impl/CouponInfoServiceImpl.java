package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.CouponInfo;
import cn.itsource.aigou.core.mapper.CouponInfoMapper;
import cn.itsource.aigou.service.ICouponInfoService;
@Service
public class CouponInfoServiceImpl extends BaseServiceImpl<CouponInfo> implements ICouponInfoService{
	@Autowired
	private CouponInfoMapper mapper;
	
	@Override
	protected BaseMapper<CouponInfo> getMapper() {
		return mapper;
	}
}
