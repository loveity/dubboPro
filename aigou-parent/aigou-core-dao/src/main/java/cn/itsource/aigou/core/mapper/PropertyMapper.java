package cn.itsource.aigou.core.mapper;

import java.util.List;

import cn.itsource.aigou.core.domain.Property;
import cn.itsource.aigou.core.domain.PropertyOption;
import cn.itsource.aigou.core.query.BaseQuery;

public interface PropertyMapper extends BaseMapper<Property> {

	List<Property> getProperties(BaseQuery qObject);

	/**
	 * 通过商品ID获取属性集合
	 * @param productId
	 * @return
	 */
	List<Property> getProductProperties(Long productId);

	/**
	 * 通过属性ID获取选项集合
	 * @param id
	 * @return
	 */
	List<PropertyOption> getOptions(Long id);
}