package cn.itsource.aigou.facade;

import java.util.List;
import java.util.Map;

import cn.itsource.aigou.core.domain.Brand;
import cn.itsource.aigou.core.domain.Product;
import cn.itsource.aigou.core.domain.ProductExt;
import cn.itsource.aigou.core.domain.ProductMedia;
import cn.itsource.aigou.core.domain.ProductType;
import cn.itsource.aigou.core.domain.ProductViewProperty;
import cn.itsource.aigou.core.domain.Property;
import cn.itsource.aigou.core.domain.PropertyOption;
import cn.itsource.aigou.core.domain.Sku;
import cn.itsource.aigou.core.query.BaseQuery;
import cn.itsource.aigou.core.utils.Page;
/**
 * 商品中心统一服务接口
 * @author nixianhua
 *
 */
public interface ProductCenterService {
	/**
	 * 获取指定ID的商品
	 * @param id
	 * @return
	 */
	Product getProduct(Long id);
	
	/**
	 * 获取产品分页列表
	 * @param qObject
	 * @return
	 */
	Page<Product> getProductPage(BaseQuery qObject);
	
	/**
	 * 新增商品
	 * @param product
	 */
	Product saveOrUpdateProduct(Product product);
	
	/**
	 * 删除指定的商品
	 * @param id
	 */
	void deleteProduct(String id);

	/**
	 * 获取指定ID的商品类型
	 * @param id
	 * @return
	 */
	ProductType getProductType(Long id);

	/**
	 * 保存商品类型
	 * @param productType
	 */
	ProductType saveOrUpdateProductType(ProductType productType);

	/**
	 * 获取所有的商品分类
	 * @return
	 */
	List<ProductType> getAllProductType();
	
	/**
	 * 获取树形的产品目录
	 * @return
	 */
	List<ProductType> getAllProductTypeTree();
	
	/**
	 * 获取面包屑分类名及分类列表
	 * @param productTypeId
	 * Map: currentType => ProductType
	 * 		otherTypes => List ProductType
	 * @return
	 */
	List<Map<String, Object>> getTypeBreadcrumb(Long productTypeId);
	
	/**
	 * 通过多个ID删除商品分类
	 * @param ids
	 */
	void deleteProductType(String ids);

	/**
	 * 获取属性信息
	 * @param id
	 * @return
	 */
	Property getProperty(Long id);

	/**
	 * 删除指定id的属性
	 * @param id
	 */
	void deleteProperty(String id);

	/**
	 * 修改属性信息
	 * @param property
	 */
	Property saveOrUpdateProperty(Property property);

	/**
	 * 通过查询对象获取属性列表
	 * @param qObject
	 * @return
	 */
	List<Property> getProperties(BaseQuery qObject);

	/**
	 * 通过ID获取选项
	 * @param id
	 * @return
	 */
	PropertyOption getPropertyOption(Long id);

	/**
	 * 删除指定ID的属性选项
	 * @param ids
	 */
	void deletePropertyOption(String ids);

	/**
	 * 保存属性选项
	 * @param propertyOption
	 */
	PropertyOption saveOrUpdatePropertyOption(PropertyOption propertyOption);

	/**
	 * 通过查询对象查询选项值列表
	 * @param qObject
	 * @return
	 */
	List<PropertyOption> getPropertieOptions(BaseQuery qObject);

	/**
	 * 获取品牌分页列表
	 * @param qObject
	 * @return
	 */
	Page<Brand> getBrandPage(BaseQuery qObject);

	/**
	 * 通过ID获取品牌信息
	 * @param id
	 * @return
	 */
	Brand getBrand(Long id);
	
	/**
	 * 通过商品类型获取品牌列表
	 * @param productType
	 * @return
	 */
	List<Brand> getBrandsByProductType(Long productType);

	/**
	 * 保存品牌信息
	 * @param brand
	 */
	Brand saveOrUpdateBrand(Brand brand);

	/**
	 * 删除品牌信息
	 * @param ids
	 */
	void deleteBrand(String ids);

	/**
	 * 通过商品ID获取属性集
	 * @param id
	 * @return
	 */
	List<Property> getProductProperties(Long id);
	
	/**
	 * 通过商品ID获取显示属性集
	 * @param id
	 * @return
	 */
	List<Property> getViewProperties(Long id);

	/**
	 * 通过商品ID获取sku属性集
	 * @param id
	 * @return
	 */
	List<Property> getSkuProperties(Long id);

	/**
	 * 获取指定属性的选项集合
	 * @param id
	 * @return
	 */
	List<PropertyOption> getPropertyOptions(Long id);

	/**
	 * 保存商品显示属性集合
	 * @param productId
	 * @param productViewPropertyList
	 */
	void saveProductViewProperties(Long productId,List<ProductViewProperty> productViewPropertyList);

	/**
	 * 通过商品ID获取已设置的显示属性集及其选项值
	 * @param id
	 * @return
	 */
	List<ProductViewProperty> getProductViewProperties(Long id);

	/**
	 * 获取商品的SKU列表
	 * @param productId
	 * @return
	 */
	List<Sku> getSkuList(Long productId);

	/**
	 * 保存sku信息
	 * @param sku
	 */
	void saveSku(Sku sku);

	/**
	 * 更新sku
	 * @param sku
	 */
	void updateSku(Sku sku);
	
	/**
	 * 获取SKU信息
	 * @param id
	 * @return
	 */
	Sku getSku(Long id);

	/**
	 * 获取商品的媒体列表
	 * @param id
	 * @return
	 */
	List<ProductMedia> getProductMedias(Long id);

	/**
	 * 获取商品的扩展信息
	 * @param id
	 * @return
	 */
	ProductExt getProductExt(Long id);

	/**
	 * 上架/下架指定的商品集合（ID）
	 * @param idArr 商品ID数组
	 * @param onsale 是否上架
	 */
	void onSale(Long[] idArr, Integer onsale);

	/**
	 * 恢复sku锁定的库存
	 * @param skuId
	 * @param amount
	 */
	void recoverSkuStock(Long skuId, Integer amount);

	/**
	 * sku出库
	 * @param skuId
	 * @param amount
	 */
	void outboudSku(Long skuId, Integer amount);

	/**
	 * 通过sku code获取sku信息
	 * @param skuCode
	 * @return
	 */
	Sku getSkuByCode(String skuCode);

	
	
}
