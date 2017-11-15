package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.Employee;
import cn.itsource.aigou.core.mapper.EmployeeMapper;
import cn.itsource.aigou.service.IEmployeeService;
@Service
public class EmployeeServiceImpl extends BaseServiceImpl<Employee> implements IEmployeeService{
	@Autowired
	private EmployeeMapper mapper;
	
	@Override
	protected BaseMapper<Employee> getMapper() {
		return mapper;
	}
}
