package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.GlobalInfo;
import cn.itsource.aigou.core.mapper.GlobalInfoMapper;
import cn.itsource.aigou.service.IGlobalInfoService;
@Service
public class GlobalInfoServiceImpl extends BaseServiceImpl<GlobalInfo> implements IGlobalInfoService{
	@Autowired
	private GlobalInfoMapper mapper;
	
	@Override
	protected BaseMapper<GlobalInfo> getMapper() {
		return mapper;
	}
}
