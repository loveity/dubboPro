package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.VipProductView;
import cn.itsource.aigou.core.mapper.VipProductViewMapper;
import cn.itsource.aigou.service.IVipProductViewService;
@Service
public class VipProductViewServiceImpl extends BaseServiceImpl<VipProductView> implements IVipProductViewService{
	@Autowired
	private VipProductViewMapper mapper;
	
	@Override
	protected BaseMapper<VipProductView> getMapper() {
		return mapper;
	}
}
