package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.ProductMedia;
import cn.itsource.aigou.core.mapper.ProductMediaMapper;
import cn.itsource.aigou.service.IProductMediaService;
@Service
public class ProductMediaServiceImpl extends BaseServiceImpl<ProductMedia> implements IProductMediaService{
	@Autowired
	private ProductMediaMapper mapper;
	
	@Override
	protected BaseMapper<ProductMedia> getMapper() {
		return mapper;
	}
}
