package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.PromotionScope;
import cn.itsource.aigou.core.mapper.PromotionScopeMapper;
import cn.itsource.aigou.service.IPromotionScopeService;
@Service
public class PromotionScopeServiceImpl extends BaseServiceImpl<PromotionScope> implements IPromotionScopeService{
	@Autowired
	private PromotionScopeMapper mapper;
	
	@Override
	protected BaseMapper<PromotionScope> getMapper() {
		return mapper;
	}
}
