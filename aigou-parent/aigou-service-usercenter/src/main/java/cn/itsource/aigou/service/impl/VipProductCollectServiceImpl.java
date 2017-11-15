package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.VipProductCollect;
import cn.itsource.aigou.core.mapper.VipProductCollectMapper;
import cn.itsource.aigou.service.IVipProductCollectService;
@Service
public class VipProductCollectServiceImpl extends BaseServiceImpl<VipProductCollect> implements IVipProductCollectService{
	@Autowired
	private VipProductCollectMapper mapper;
	
	@Override
	protected BaseMapper<VipProductCollect> getMapper() {
		return mapper;
	}
}
