package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.Department;
import cn.itsource.aigou.core.mapper.DepartmentMapper;
import cn.itsource.aigou.service.IDepartmentService;
@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department> implements IDepartmentService{
	@Autowired
	private DepartmentMapper mapper;
	
	@Override
	protected BaseMapper<Department> getMapper() {
		return mapper;
	}
}
