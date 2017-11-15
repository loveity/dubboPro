package cn.itsource.aigou.web.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itsource.aigou.core.domain.ProductType;
import cn.itsource.aigou.core.utils.Page;
import cn.itsource.aigou.core.utils.Ret;
import cn.itsource.aigou.facade.ProductCenterService;
import cn.itsource.aigou.facade.query.ProductTypeQuery;
import cn.itsource.aigou.web.consts.ControllerConsts;

@Controller
@RequestMapping("/"+ProductTypeController.DOMAIN)
public class ProductTypeController {
	public static final String DOMAIN = "productType";

	@Reference
	private ProductCenterService productCenterService;
	
	@RequestMapping(ControllerConsts.URL_INDEX)
	public String index() {
		return DOMAIN + ControllerConsts.VIEW_INDEX;
	}

	@RequestMapping(ControllerConsts.URL_JSON)
	@ResponseBody
	public Page<ProductType> json(ProductTypeQuery qObject) {
		Page<ProductType> pageResult = null;
		return pageResult;
	}

	@RequestMapping(ControllerConsts.URL_EDIT)
	public String edit(Long id, Model model) {
		return DOMAIN + ControllerConsts.VIEW_EDIT;
	}
	
	@RequestMapping(ControllerConsts.URL_DETAIL)
	@ResponseBody
	public Object detail(Long id){
		ProductType o = productCenterService.getProductType(id);
		return o;
	}

	@RequestMapping(ControllerConsts.URL_STORE)
	@ResponseBody
	public Ret store(ProductType productType) {
		productCenterService.saveOrUpdateProductType(productType);
		return Ret.me();
	}

	@RequestMapping(ControllerConsts.URL_DELETE)
	@ResponseBody
	public Ret delete(String id) {
		if(StringUtils.isBlank(id)){
			return Ret.me().setSuccess(false).setInfo("无效的ID");
		}
		productCenterService.deleteProductType(id);
		return Ret.me();
	}

	@RequestMapping(ControllerConsts.URL_SHOW)
	public String show(Long id, Model model) {
		return DOMAIN + ControllerConsts.VIEW_SHOW;
	}
	
	@RequestMapping(ControllerConsts.URL_TREE_JSON)
	@ResponseBody
	public List<ProductType> tree(){
		 List<ProductType> productTypes = productCenterService.getAllProductTypeTree();
		return productTypes;
	}

}
