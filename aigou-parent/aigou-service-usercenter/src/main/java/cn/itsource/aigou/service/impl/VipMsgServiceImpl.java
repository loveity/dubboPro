package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.VipMsg;
import cn.itsource.aigou.core.mapper.VipMsgMapper;
import cn.itsource.aigou.service.IVipMsgService;
@Service
public class VipMsgServiceImpl extends BaseServiceImpl<VipMsg> implements IVipMsgService{
	@Autowired
	private VipMsgMapper mapper;
	
	@Override
	protected BaseMapper<VipMsg> getMapper() {
		return mapper;
	}
}
