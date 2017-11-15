package cn.itsource.aigou.core.mapper;

import cn.itsource.aigou.core.domain.ProductExt;

public interface ProductExtMapper extends BaseMapper<ProductExt> {

	/**
	 * 通过商品ID获取商品扩展信息
	 * @param id
	 * @return
	 */
	ProductExt getByProductId(Long id);
}