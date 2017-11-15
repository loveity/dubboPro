package cn.itsource.aigou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.PropertyOption;
import cn.itsource.aigou.core.mapper.PropertyOptionMapper;
import cn.itsource.aigou.core.query.BaseQuery;
import cn.itsource.aigou.service.IPropertyOptionService;
@Service
public class PropertyOptionServiceImpl extends BaseServiceImpl<PropertyOption> implements IPropertyOptionService{
	@Autowired
	private PropertyOptionMapper mapper;
	
	@Override
	protected BaseMapper<PropertyOption> getMapper() {
		return mapper;
	}

	@Override
	public List<PropertyOption> getPropertieOptions(BaseQuery qObject) {
		return mapper.getPropertieOptions(qObject);
	}
}
