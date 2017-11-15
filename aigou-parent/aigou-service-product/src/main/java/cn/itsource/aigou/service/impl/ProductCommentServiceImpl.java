package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.ProductComment;
import cn.itsource.aigou.core.mapper.ProductCommentMapper;
import cn.itsource.aigou.service.IProductCommentService;
@Service
public class ProductCommentServiceImpl extends BaseServiceImpl<ProductComment> implements IProductCommentService{
	@Autowired
	private ProductCommentMapper mapper;
	
	@Override
	protected BaseMapper<ProductComment> getMapper() {
		return mapper;
	}
}
