package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.SmsCode;
import cn.itsource.aigou.core.mapper.SmsCodeMapper;
import cn.itsource.aigou.service.ISmsCodeService;
@Service
public class SmsCodeServiceImpl extends BaseServiceImpl<SmsCode> implements ISmsCodeService{
	@Autowired
	private SmsCodeMapper mapper;
	
	@Override
	protected BaseMapper<SmsCode> getMapper() {
		return mapper;
	}
}
