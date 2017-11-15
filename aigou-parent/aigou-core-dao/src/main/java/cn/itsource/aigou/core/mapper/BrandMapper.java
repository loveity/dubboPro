package cn.itsource.aigou.core.mapper;

import java.util.List;

import cn.itsource.aigou.core.domain.Brand;

public interface BrandMapper extends BaseMapper<Brand> {

	/**
	 * 通过商品类型获取品牌列表
	 * @param productType
	 * @return
	 */
	List<Brand> getBrandsByProductType(Long productType);
}