package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.Dictionary;
import cn.itsource.aigou.core.mapper.DictionaryMapper;
import cn.itsource.aigou.service.IDictionaryService;
@Service
public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary> implements IDictionaryService{
	@Autowired
	private DictionaryMapper mapper;
	
	@Override
	protected BaseMapper<Dictionary> getMapper() {
		return mapper;
	}
}
