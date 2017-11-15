package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.Permission;
import cn.itsource.aigou.core.mapper.PermissionMapper;
import cn.itsource.aigou.service.IPermissionService;
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements IPermissionService{
	@Autowired
	private PermissionMapper mapper;
	
	@Override
	protected BaseMapper<Permission> getMapper() {
		return mapper;
	}
}
