package cn.itsource.aigou.core.common.base.impl;

import java.util.List;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.IBaseService;
import cn.itsource.aigou.core.query.BaseQuery;
import cn.itsource.aigou.core.utils.Page;

public abstract class BaseServiceImpl<T> implements IBaseService<T> {

	protected abstract BaseMapper<T> getMapper();
	
	@Override
	public void save(T o) {
		getMapper().save(o);
	}

	@Override
	public void savePart(T o) {
		getMapper().savePart(o);
	}

	@Override
	public void delete(Long id) {
		getMapper().delete(id);
	}

	@Override
	public void update(T o) {
		getMapper().update(o);
	}

	@Override
	public void updatePart(T o) {
		getMapper().updatePart(o);
	}

	@Override
	public T get(Long id) {
		return getMapper().get(id);
	}
	
	@Override
	public int queryTotal(BaseQuery query) {
		return getMapper().queryTotal(query);
	}
	
	@Override
	public List<T> query(BaseQuery query) {
		return getMapper().query(query);
	}
	
	@Override
	public Page<T> queryPage(BaseQuery query) {
		int total = this.queryTotal(query);
		List<T> rows = this.query(query);
		return new Page<>(rows, total, query);
	}
}
