package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.VipLoginLog;
import cn.itsource.aigou.core.mapper.VipLoginLogMapper;
import cn.itsource.aigou.service.IVipLoginLogService;
@Service
public class VipLoginLogServiceImpl extends BaseServiceImpl<VipLoginLog> implements IVipLoginLogService{
	@Autowired
	private VipLoginLogMapper mapper;
	
	@Override
	protected BaseMapper<VipLoginLog> getMapper() {
		return mapper;
	}
}
