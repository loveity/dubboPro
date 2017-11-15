package cn.itsource.aigou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.Property;
import cn.itsource.aigou.core.domain.PropertyOption;
import cn.itsource.aigou.core.mapper.PropertyMapper;
import cn.itsource.aigou.core.query.BaseQuery;
import cn.itsource.aigou.service.IPropertyService;
@Service
public class PropertyServiceImpl extends BaseServiceImpl<Property> implements IPropertyService{
	@Autowired
	private PropertyMapper mapper;
	
	@Override
	protected BaseMapper<Property> getMapper() {
		return mapper;
	}

	@Override
	public List<Property> getProperties(BaseQuery qObject) {
		return mapper.getProperties(qObject);
	}

	@Override
	public List<PropertyOption> getOptions(Long id) {
		return mapper.getOptions(id);
	}
}
