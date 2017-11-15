package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.PromotionScopeValue;
import cn.itsource.aigou.core.mapper.PromotionScopeValueMapper;
import cn.itsource.aigou.service.IPromotionScopeValueService;
@Service
public class PromotionScopeValueServiceImpl extends BaseServiceImpl<PromotionScopeValue> implements IPromotionScopeValueService{
	@Autowired
	private PromotionScopeValueMapper mapper;
	
	@Override
	protected BaseMapper<PromotionScopeValue> getMapper() {
		return mapper;
	}
}
