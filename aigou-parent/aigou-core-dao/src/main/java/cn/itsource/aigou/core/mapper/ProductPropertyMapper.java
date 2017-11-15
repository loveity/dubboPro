package cn.itsource.aigou.core.mapper;

import java.util.List;

import cn.itsource.aigou.core.domain.ProductProperty;
import cn.itsource.aigou.core.domain.Property;

public interface ProductPropertyMapper extends BaseMapper<ProductProperty> {

	/**
	 * 通过商品ID获取属性集合
	 * @param productId
	 * @return
	 */
	List<Property> getProperties(Long productId);
}