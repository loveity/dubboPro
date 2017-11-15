package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.SmsLog;
import cn.itsource.aigou.core.mapper.SmsLogMapper;
import cn.itsource.aigou.service.ISmsLogService;
@Service
public class SmsLogServiceImpl extends BaseServiceImpl<SmsLog> implements ISmsLogService{
	@Autowired
	private SmsLogMapper mapper;
	
	@Override
	protected BaseMapper<SmsLog> getMapper() {
		return mapper;
	}
}
