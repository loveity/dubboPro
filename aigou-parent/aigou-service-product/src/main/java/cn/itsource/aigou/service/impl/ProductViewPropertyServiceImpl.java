package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.ProductViewProperty;
import cn.itsource.aigou.core.mapper.ProductViewPropertyMapper;
import cn.itsource.aigou.service.IProductViewPropertyService;
@Service
public class ProductViewPropertyServiceImpl extends BaseServiceImpl<ProductViewProperty> implements IProductViewPropertyService{
	@Autowired
	private ProductViewPropertyMapper mapper;
	
	@Override
	protected BaseMapper<ProductViewProperty> getMapper() {
		return mapper;
	}
}
