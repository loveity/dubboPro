package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.CmsType;
import cn.itsource.aigou.core.mapper.CmsTypeMapper;
import cn.itsource.aigou.service.ICmsTypeService;
@Service
public class CmsTypeServiceImpl extends BaseServiceImpl<CmsType> implements ICmsTypeService{
	@Autowired
	private CmsTypeMapper mapper;
	
	@Override
	protected BaseMapper<CmsType> getMapper() {
		return mapper;
	}
}
