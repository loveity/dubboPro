package cn.itsource.aigou.service;

import java.util.List;

import cn.itsource.aigou.core.common.base.IBaseService;
import cn.itsource.aigou.core.domain.Property;
import cn.itsource.aigou.core.domain.PropertyOption;
import cn.itsource.aigou.core.query.BaseQuery;

public interface IPropertyService extends IBaseService<Property> {

	/**
	 * 通过查询对象获取属性集合
	 * @param productId
	 * @return
	 */
	List<Property> getProperties(BaseQuery qObject);

	/**
	 * 通过属性ID获取选项集合
	 * @param id
	 * @return
	 */
	List<PropertyOption> getOptions(Long id);
	
}
