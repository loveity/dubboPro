package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.Cms;
import cn.itsource.aigou.core.mapper.CmsMapper;
import cn.itsource.aigou.service.ICmsService;
@Service
public class CmsServiceImpl extends BaseServiceImpl<Cms> implements ICmsService{
	@Autowired
	private CmsMapper mapper;
	
	@Override
	protected BaseMapper<Cms> getMapper() {
		return mapper;
	}
}
