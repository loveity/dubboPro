package cn.itsource.aigou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.ProductProperty;
import cn.itsource.aigou.core.domain.Property;
import cn.itsource.aigou.core.mapper.ProductPropertyMapper;
import cn.itsource.aigou.service.IProductPropertyService;
@Service
public class ProductPropertyServiceImpl extends BaseServiceImpl<ProductProperty> implements IProductPropertyService{
	@Autowired
	private ProductPropertyMapper mapper;
	
	@Override
	protected BaseMapper<ProductProperty> getMapper() {
		return mapper;
	}
}
