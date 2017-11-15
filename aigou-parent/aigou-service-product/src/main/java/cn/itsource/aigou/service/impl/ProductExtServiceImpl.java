package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.ProductExt;
import cn.itsource.aigou.core.mapper.ProductExtMapper;
import cn.itsource.aigou.service.IProductExtService;
@Service
public class ProductExtServiceImpl extends BaseServiceImpl<ProductExt> implements IProductExtService{
	@Autowired
	private ProductExtMapper mapper;
	
	@Override
	protected BaseMapper<ProductExt> getMapper() {
		return mapper;
	}

	@Override
	public ProductExt getByProductId(Long id) {
		return mapper.getByProductId(id);
	}
}
