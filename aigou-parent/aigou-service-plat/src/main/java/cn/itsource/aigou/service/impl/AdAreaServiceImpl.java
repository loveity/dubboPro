package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.AdArea;
import cn.itsource.aigou.core.mapper.AdAreaMapper;
import cn.itsource.aigou.service.IAdAreaService;
@Service
public class AdAreaServiceImpl extends BaseServiceImpl<AdArea> implements IAdAreaService{
	@Autowired
	private AdAreaMapper mapper;
	
	@Override
	protected BaseMapper<AdArea> getMapper() {
		return mapper;
	}
}
