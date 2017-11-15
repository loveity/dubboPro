package cn.itsource.aigou.core.common.base;

import java.util.List;

import cn.itsource.aigou.core.query.BaseQuery;

public interface BaseMapper<T> {
	void save(T o);
	void savePart(T o);
	void delete(Long id);
	void update(T o);
	void updatePart(T o);
	T get(Long id);
	
	int queryTotal(BaseQuery query);
	List<T> query(BaseQuery query);
}
