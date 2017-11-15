package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.PromotionReturn;
import cn.itsource.aigou.core.mapper.PromotionReturnMapper;
import cn.itsource.aigou.service.IPromotionReturnService;
@Service
public class PromotionReturnServiceImpl extends BaseServiceImpl<PromotionReturn> implements IPromotionReturnService{
	@Autowired
	private PromotionReturnMapper mapper;
	
	@Override
	protected BaseMapper<PromotionReturn> getMapper() {
		return mapper;
	}
}
