package cn.itsource.aigou.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.itsource.aigou.core.domain.Sku;
import cn.itsource.aigou.core.domain.SkuProperty;

public interface SkuMapper extends BaseMapper<Sku> {

	/**
	 * 保存SKU的属性集
	 * @param skuPropertyList
	 */
	void saveProperties(List<SkuProperty> skuPropertyList);
	
	/**
	 * 通过skuid删除sku的属性集合
	 * @param id
	 */
	void deleteProperties(Long id);

	/**
	 * 更新商品的价格区间
	 * @param productId
	 */
	void updateProductPrice(Long productId);

	/**
	 * 获取指定商品的最大的SKU CODE
	 * @param productId
	 * @return
	 */
	String getMaxCode(Long productId);

	/**
	 * 恢复sku锁定的库存
	 * @param skuId
	 * @param amount
	 */
	void recoverStock(@Param("skuId") Long skuId, @Param("amount")  Integer amount);

	/**
	 * sku确认出库
	 * @param skuId
	 * @param amount
	 */
	void outbound(@Param("skuId") Long skuId, @Param("amount")  Integer amount);

	/**
	 * 获取sku code获取sku信息
	 * @param skuCode
	 * @return
	 */
	Sku getByCode(String skuCode);
	
}