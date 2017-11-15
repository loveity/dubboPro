package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.Role;
import cn.itsource.aigou.core.mapper.RoleMapper;
import cn.itsource.aigou.service.IRoleService;
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService{
	@Autowired
	private RoleMapper mapper;
	
	@Override
	protected BaseMapper<Role> getMapper() {
		return mapper;
	}
}
