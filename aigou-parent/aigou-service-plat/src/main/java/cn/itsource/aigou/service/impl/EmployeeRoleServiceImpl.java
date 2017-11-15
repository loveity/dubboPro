package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.EmployeeRole;
import cn.itsource.aigou.core.mapper.EmployeeRoleMapper;
import cn.itsource.aigou.service.IEmployeeRoleService;
@Service
public class EmployeeRoleServiceImpl extends BaseServiceImpl<EmployeeRole> implements IEmployeeRoleService{
	@Autowired
	private EmployeeRoleMapper mapper;
	
	@Override
	protected BaseMapper<EmployeeRole> getMapper() {
		return mapper;
	}
}
