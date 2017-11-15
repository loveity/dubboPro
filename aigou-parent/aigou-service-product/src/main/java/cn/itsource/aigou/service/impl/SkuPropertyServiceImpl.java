package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.SkuProperty;
import cn.itsource.aigou.core.mapper.SkuPropertyMapper;
import cn.itsource.aigou.service.ISkuPropertyService;
@Service
public class SkuPropertyServiceImpl extends BaseServiceImpl<SkuProperty> implements ISkuPropertyService{
	@Autowired
	private SkuPropertyMapper mapper;
	
	@Override
	protected BaseMapper<SkuProperty> getMapper() {
		return mapper;
	}
}
