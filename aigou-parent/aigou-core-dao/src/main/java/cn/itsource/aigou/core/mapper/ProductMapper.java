package cn.itsource.aigou.core.mapper;

import java.util.List;
import java.util.Map;

import cn.itsource.aigou.core.domain.Product;
import cn.itsource.aigou.core.domain.ProductExt;
import cn.itsource.aigou.core.domain.ProductMedia;
import cn.itsource.aigou.core.domain.ProductViewProperty;
import cn.itsource.aigou.core.domain.Property;
import cn.itsource.aigou.core.domain.Sku;

public interface ProductMapper extends BaseMapper<Product> {

	/**
	 * 通过商品id获取属性集合
	 * @param id
	 * @return
	 */
	List<Property> getProperties(Long id);
	
	/**
	 * 通过商品id获取显示属性集合
	 * @param id
	 * @return
	 */
	List<Property> getViewProperties(Long productId);
	
	/**
	 * 通过商品id获取Sku属性集合
	 * @param id
	 * @return
	 */
	List<Property> getSkuProperties(Long productId);

	/**
	 * 通过商品ID删除属性集合
	 * @param productId
	 */
	void deleteProperties(Long productId);

	/**
	 * 批量添加商品属性集
	 * @param maps
	 */
	void saveProperties(List<Map<String, Long>> maps);

	/**
	 * 保存商品显示属性集合
	 * @param productViewPropertyList
	 */
	void saveViewProperties(List<ProductViewProperty> productViewPropertyList);

	/**
	 * 根据商品ID删除其显示属性集
	 * @param id
	 */
	void deleteViewProperties(Long id);

	/**
	 * 获取指定商品ID已设置的显示属性集及选项值
	 * @param id
	 * @return
	 */
	List<ProductViewProperty> getSelectedViewProperties(Long id);

	/**
	 * 获取商品的SKU列表
	 * @param productId
	 * @return
	 */
	List<Sku> getSkuList(Long productId);

	/**
	 * 通过商品ID删除其所有媒体资源
	 * @param id
	 */
	void deleteMedias(Long id);

	/**
	 * 批量添加商品的媒体资源列表
	 * @param productMediaList
	 */
	void saveMedias(List<ProductMedia> productMediaList);

	/**
	 * 通过商品ID获取其媒体列表
	 * @param id
	 * @return
	 */
	List<ProductMedia> getMedias(Long id);

	/**
	 * 获取商品的扩展信息
	 * @param id
	 * @return
	 */
	ProductExt getExt(Long id);

	/**
	 * 商品下架
	 * @param idArr
	 */
	void offSale(Map<String, Object> params);

	/**
	 * 商品上架
	 * @param idArr
	 */
	void onSale(Map<String, Object> params);
	
	/**
	 *  更新商品显示属性
	 * @param productId
	 * @param viewProperties
	 */
	void updateProductViewProperties(Long productId, String viewProperties);
}