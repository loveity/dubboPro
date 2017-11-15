package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.VipRealinfo;
import cn.itsource.aigou.core.mapper.VipRealinfoMapper;
import cn.itsource.aigou.service.IVipRealinfoService;
@Service
public class VipRealinfoServiceImpl extends BaseServiceImpl<VipRealinfo> implements IVipRealinfoService{
	@Autowired
	private VipRealinfoMapper mapper;
	
	@Override
	protected BaseMapper<VipRealinfo> getMapper() {
		return mapper;
	}
}
