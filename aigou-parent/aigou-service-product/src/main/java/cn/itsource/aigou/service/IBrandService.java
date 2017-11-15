package cn.itsource.aigou.service;

import java.util.List;

import cn.itsource.aigou.core.common.base.IBaseService;
import cn.itsource.aigou.core.domain.Brand;

public interface IBrandService extends IBaseService<Brand> {

	/**
	 * 通过商品类型获取品牌列表
	 * @param productType
	 * @return
	 */
	List<Brand> getBrandsByProductType(Long productType);
	
}
