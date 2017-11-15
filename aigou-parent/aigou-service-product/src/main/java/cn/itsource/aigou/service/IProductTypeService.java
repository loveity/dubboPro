package cn.itsource.aigou.service;

import java.util.List;
import java.util.Map;

import cn.itsource.aigou.core.common.base.IBaseService;
import cn.itsource.aigou.core.domain.ProductType;

public interface IProductTypeService extends IBaseService<ProductType> {

	/**
	 * 获取所有普通类型列表
	 * @return
	 */
	List<ProductType> getAll();

	/**
	 * 获取所有树状类型数据
	 * @param pid 根节点
	 * @return
	 */
	List<ProductType> getAllTree(Long pid);

	/**
	 * 获取面包屑分类名及分类列表
	 * @param productTypeId
	 * Map: currentType => ProductType
	 * 		otherTypes => List ProductType
	 * @return
	 */
	List<Map<String, Object>> getTypeBreadcrumb(Long productTypeId);
	
}
