package cn.itsource.aigou.core.mapper;

import java.util.List;

import cn.itsource.aigou.core.domain.ProductType;

public interface ProductTypeMapper extends BaseMapper<ProductType> {

	List<ProductType> getAll();

	/**
	 * 通过pid获取子节点
	 * @param pid
	 */
	List<ProductType> getChildren(Long pid);

	/**
	 * 根据父节点获取节点的所有后代节点
	 * @param pid
	 * @return
	 */
	List<ProductType> getTypes(Long pid);
}