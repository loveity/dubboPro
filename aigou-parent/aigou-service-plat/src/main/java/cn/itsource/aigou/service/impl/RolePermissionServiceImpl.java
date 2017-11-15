package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.RolePermission;
import cn.itsource.aigou.core.mapper.RolePermissionMapper;
import cn.itsource.aigou.service.IRolePermissionService;
@Service
public class RolePermissionServiceImpl extends BaseServiceImpl<RolePermission> implements IRolePermissionService{
	@Autowired
	private RolePermissionMapper mapper;
	
	@Override
	protected BaseMapper<RolePermission> getMapper() {
		return mapper;
	}
}
