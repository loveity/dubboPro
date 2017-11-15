package cn.itsource.aigou.service;

import cn.itsource.aigou.core.common.base.IBaseService;
import cn.itsource.aigou.core.domain.ProductExt;

public interface IProductExtService extends IBaseService<ProductExt> {

	/**
	 * 通过商品ID获取商品扩展信息
	 * @param id
	 * @return
	 */
	ProductExt getByProductId(Long id);
	
}
