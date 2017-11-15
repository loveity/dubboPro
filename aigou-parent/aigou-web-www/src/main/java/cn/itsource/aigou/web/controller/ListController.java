package cn.itsource.aigou.web.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itsource.aigou.core.domain.Brand;
import cn.itsource.aigou.core.utils.Page;
import cn.itsource.aigou.facade.CommonService;
import cn.itsource.aigou.facade.ProductCenterService;
import cn.itsource.aigou.facade.query.ProductQuery;

@Controller
public class ListController {
	@Reference
	private CommonService commonService;
	
	@Reference
	private ProductCenterService productCenterService;
	
	@RequestMapping("/list")
	public String list(ProductQuery query,Model model) {
		
		query.setRows(16);
		if(null!=query.getPriceMin()){
			query.setPriceMin(100 * query.getPriceMin());
		}
		if(null!=query.getPriceMax()){
			query.setPriceMax(100 * query.getPriceMax());
		}
		//通过es查询商品信息
		Page<Map<String, Object>> productPage = commonService.queryEsProducts(query);
		model.addAttribute("page", productPage);
		
		if(query.getProductType()!=null){
			List<Brand> brands = productCenterService.getBrandsByProductType(query.getProductType());
			//获取所有品牌的首字母集合
			Set<String> letterList = new HashSet<String>();
			if(brands!=null){
				for (Brand brand : brands) {
					letterList.add(brand.getFirstLetter());
				}
			}
			//获取面包屑列表
			List<Map<String, Object>> breads = productCenterService.getTypeBreadcrumb(query.getProductType());
			
			//置顶选中的品牌
			Long brandId = query.getBrand();
			if(brandId!=null && brands!=null){
				int index = 0;
				for (;index<brands.size();index++) {
					Brand brand = brands.get(index);
					Long theBrandId = brand.getId();
					if(theBrandId.longValue() == brandId){
						break;
					}
				}
				if(index>0){
					Brand brand = brands.get(index);
					brands.remove(index);
					brands.add(0, brand);
				}
			}
			model.addAttribute("letters", letterList);
			model.addAttribute("brands", brands);
			model.addAttribute("breads", breads);
		}
		return "list";
	}
}