package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.DictionaryValue;
import cn.itsource.aigou.core.mapper.DictionaryValueMapper;
import cn.itsource.aigou.service.IDictionaryValueService;
@Service
public class DictionaryValueServiceImpl extends BaseServiceImpl<DictionaryValue> implements IDictionaryValueService{
	@Autowired
	private DictionaryValueMapper mapper;
	
	@Override
	protected BaseMapper<DictionaryValue> getMapper() {
		return mapper;
	}
}
