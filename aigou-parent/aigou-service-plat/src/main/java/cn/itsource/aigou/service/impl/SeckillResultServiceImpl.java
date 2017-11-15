package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.SeckillResult;
import cn.itsource.aigou.core.mapper.SeckillResultMapper;
import cn.itsource.aigou.service.ISeckillResultService;
@Service
public class SeckillResultServiceImpl extends BaseServiceImpl<SeckillResult> implements ISeckillResultService{
	@Autowired
	private SeckillResultMapper mapper;
	
	@Override
	protected BaseMapper<SeckillResult> getMapper() {
		return mapper;
	}
}
