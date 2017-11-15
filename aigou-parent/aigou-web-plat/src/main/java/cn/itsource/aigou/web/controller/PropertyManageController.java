package cn.itsource.aigou.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itsource.aigou.web.consts.ControllerConsts;

@Controller
@RequestMapping("/"+PropertyManageController.DOMAIN)
public class PropertyManageController {
	public static final String DOMAIN = "propertyManage";
	
	@RequestMapping(ControllerConsts.URL_INDEX)
	public String index() {
		return DOMAIN + ControllerConsts.VIEW_INDEX;
	}

}
