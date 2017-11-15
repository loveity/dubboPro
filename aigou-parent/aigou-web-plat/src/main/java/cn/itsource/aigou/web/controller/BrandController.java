package cn.itsource.aigou.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itsource.aigou.core.domain.Brand;
import cn.itsource.aigou.core.utils.Page;
import cn.itsource.aigou.core.utils.Ret;
import cn.itsource.aigou.facade.ProductCenterService;
import cn.itsource.aigou.facade.query.BrandQuery;
import cn.itsource.aigou.web.consts.ControllerConsts;

@Controller
@RequestMapping("/"+BrandController.DOMAIN)
public class BrandController {
	public static final String DOMAIN = "brand";
	
	@Reference
	private ProductCenterService productCenterService;

	@RequestMapping(ControllerConsts.URL_INDEX)
	public String index() {
		return DOMAIN + ControllerConsts.VIEW_INDEX;
	}

	@RequestMapping(ControllerConsts.URL_JSON)
	@ResponseBody
	public Page<Brand> json(BrandQuery qObject) {
		Page<Brand> pageResult = productCenterService.getBrandPage(qObject);
		return pageResult;
	}

	@RequestMapping(ControllerConsts.URL_EDIT)
	public String edit(Long id, Model model) {
		if(id!=null){
			Brand brand = productCenterService.getBrand(id);
			model.addAttribute("brand", brand);
		}
		return DOMAIN + ControllerConsts.VIEW_EDIT;
	}
	
	@RequestMapping(ControllerConsts.URL_DETAIL)
	@ResponseBody
	public Object detail(Long id){
		Brand o = productCenterService.getBrand(id);
		return o;
	}

	@RequestMapping(ControllerConsts.URL_STORE)
	@ResponseBody
	public Ret store(Brand brand) throws UnsupportedEncodingException {
		productCenterService.saveOrUpdateBrand(brand);
		return Ret.me();
	}

	@RequestMapping(ControllerConsts.URL_DELETE)
	@ResponseBody
	public Ret delete(String id) {
		if(StringUtils.isBlank(id)){
			return Ret.me().setSuccess(false).setInfo("无效的ID");
		}
		productCenterService.deleteBrand(id);
		return Ret.me().setSuccess(true);
	}

	@RequestMapping(ControllerConsts.URL_SHOW)
	public String show(Long id, Model model) {
		Brand o = productCenterService.getBrand(id);
		model.addAttribute("o", o);
		return DOMAIN + ControllerConsts.VIEW_SHOW;
	}
	
	@RequestMapping("/data")
	@ResponseBody
	public List<Brand> data(String q,Long v){
		List<Brand> brandList = new ArrayList<>();
		if(StringUtils.isNotBlank(q)){
			BrandQuery brandQuery = new BrandQuery();
			brandQuery.setPage(1);
			brandQuery.setRows(100);
			brandQuery.setKeyword(q);
			Page<Brand> brandPage = productCenterService.getBrandPage(brandQuery);
			brandList = brandPage.getRows();
		}else{
			if(null!=v){
				brandList.add(productCenterService.getBrand(v));
			}
		}
		return brandList;
	}
}
