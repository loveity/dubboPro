package cn.itsource.aigou.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itsource.aigou.core.domain.Property;
import cn.itsource.aigou.core.domain.PropertyOption;
import cn.itsource.aigou.core.utils.Ret;
import cn.itsource.aigou.facade.ProductCenterService;
import cn.itsource.aigou.facade.query.PropertyQuery;
import cn.itsource.aigou.web.consts.ControllerConsts;

@Controller
@RequestMapping("/"+PropertyController.DOMAIN)
public class PropertyController {
	public static final String DOMAIN = "property";
	
	@Reference
	private ProductCenterService productCenterService;

	@RequestMapping(ControllerConsts.URL_INDEX)
	public String index() {
		return DOMAIN + ControllerConsts.VIEW_INDEX;
	}

	@RequestMapping(ControllerConsts.URL_JSON)
	@ResponseBody
	public List<Property> json(PropertyQuery qObject) {
		if(null==qObject.getProductType()){
			return Collections.EMPTY_LIST;
		}
		List<Property> properties = productCenterService.getProperties(qObject);
		return properties;
	}

	@RequestMapping(ControllerConsts.URL_EDIT)
	public String edit(Long id, Model model) {
		return DOMAIN + ControllerConsts.VIEW_EDIT;
	}
	
	@RequestMapping(ControllerConsts.URL_DETAIL)
	@ResponseBody
	public Object detail(Long id){
		Property o = productCenterService.getProperty(id);
		return o;
	}

	@RequestMapping(ControllerConsts.URL_STORE)
	@ResponseBody
	public Ret store(Property property) {
		productCenterService.saveOrUpdateProperty(property);
		return Ret.me();
	}

	@RequestMapping(ControllerConsts.URL_DELETE)
	@ResponseBody
	public Ret delete(String id) {
		if(StringUtils.isBlank(id)){
			return Ret.me().setSuccess(false).setInfo("无效的ID");
		}
		productCenterService.deleteProperty(id);
		return Ret.me().setSuccess(true);
	}

	@RequestMapping(ControllerConsts.URL_SHOW)
	public String show(Long id, Model model) {
		Property o = productCenterService.getProperty(id);
		model.addAttribute("o", o);
		return DOMAIN + ControllerConsts.VIEW_SHOW;
	}
	
	@RequestMapping("/getOptions")
	@ResponseBody
	public List<PropertyOption> getOptions(Long id){
		return productCenterService.getPropertyOptions(id);
	}

}
