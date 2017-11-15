package cn.itsource.aigou.service;

import cn.itsource.aigou.core.common.base.IBaseService;
import cn.itsource.aigou.core.domain.Sku;

public interface ISkuService extends IBaseService<Sku> {

	/**
	 * 存储SKU信息（含sku的属性列表）
	 * @param sku
	 */
	void saveSku(Sku sku);

	/**
	 * 恢复sku锁定的库存
	 * @param skuId
	 * @param amount
	 */
	void recoverStock(Long skuId, Integer amount);

	/**
	 * sku出库
	 * @param skuId
	 * @param amount
	 */
	void outbound(Long skuId, Integer amount);

	/**
	 * 通过sku code获取sku
	 * @param skuCode
	 * @return
	 */
	Sku getByCode(String skuCode);
	
}
