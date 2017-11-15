package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.VipGrowLog;
import cn.itsource.aigou.core.mapper.VipGrowLogMapper;
import cn.itsource.aigou.service.IVipGrowLogService;
@Service
public class VipGrowLogServiceImpl extends BaseServiceImpl<VipGrowLog> implements IVipGrowLogService{
	@Autowired
	private VipGrowLogMapper mapper;
	
	@Override
	protected BaseMapper<VipGrowLog> getMapper() {
		return mapper;
	}
}
