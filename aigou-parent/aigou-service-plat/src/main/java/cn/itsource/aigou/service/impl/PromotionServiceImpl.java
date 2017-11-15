package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.Promotion;
import cn.itsource.aigou.core.mapper.PromotionMapper;
import cn.itsource.aigou.service.IPromotionService;
@Service
public class PromotionServiceImpl extends BaseServiceImpl<Promotion> implements IPromotionService{
	@Autowired
	private PromotionMapper mapper;
	
	@Override
	protected BaseMapper<Promotion> getMapper() {
		return mapper;
	}
}
