package cn.itsource.aigou.core.mapper;

import java.util.List;

import cn.itsource.aigou.core.domain.PropertyOption;
import cn.itsource.aigou.core.query.BaseQuery;

public interface PropertyOptionMapper extends BaseMapper<PropertyOption> {

	/**
	 * 通过查询对象获取属性选项值列表
	 * @param qObject
	 * @return
	 */
	List<PropertyOption> getPropertieOptions(BaseQuery qObject);
}