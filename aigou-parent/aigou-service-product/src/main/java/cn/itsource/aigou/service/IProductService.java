package cn.itsource.aigou.service;

import java.util.List;

import cn.itsource.aigou.core.common.base.IBaseService;
import cn.itsource.aigou.core.domain.Product;
import cn.itsource.aigou.core.domain.ProductExt;
import cn.itsource.aigou.core.domain.ProductMedia;
import cn.itsource.aigou.core.domain.ProductViewProperty;
import cn.itsource.aigou.core.domain.Property;
import cn.itsource.aigou.core.domain.Sku;

public interface IProductService extends IBaseService<Product> {
	
	/**
	 * 通过商品ID获取对应的属性集
	 * @param productId
	 * @return
	 */
	List<Property> getProperties(Long productId);
	
	/**
	 * 通过商品ID获取对应的显示属性集
	 * @param productId
	 * @return
	 */
	List<Property> getViewProperties(Long productId);

	/**
	 * 通过商品ID获取对应的SKU属性集
	 * @param productId
	 * @return
	 */
	List<Property> getSkuProperties(Long productId);

	/**
	 * 保存商品显示属性集合
	 * @param productId
	 * @param productViewPropertyList
	 */
	void storeViewProperties(Long productId,List<ProductViewProperty> productViewPropertyList);

	/**
	 * 获取指定商品ID已设置的显示属性集及选项值
	 * @param id
	 * @return
	 */
	List<ProductViewProperty> getSelectedViewProperties(Long id);

	/**
	 * 通过商品ID获取Sku列表
	 * @param productId
	 * @return
	 */
	List<Sku> getSkuList(Long productId);

	/**
	 * 获取商品的媒体资源
	 * @param id
	 * @return
	 */
	List<ProductMedia> getMedias(Long id);

	/**
	 * 获取商品的扩展信息
	 * @param id
	 * @return
	 */
	ProductExt getProductExt(Long id);

	/**
	 * 上架/下架指定的商品集合（ID）
	 * @param idArr
	 * @param onsale
	 */
	void onSale(Long[] idArr, Integer onsale);


}
