package cn.itsource.aigou.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itsource.aigou.facade.PlatManageService;

@Controller
public class MainController {
	
	@Reference
	private PlatManageService platManageService;

	
	@RequestMapping("/main")
	public String main(){
		
		return "main";
	}
	
	@RequestMapping("/region-list")
	@ResponseBody
	public Map<String, Object> regionList(String region_code, String parent_code) {
		Map<String, Object> retMap = new HashMap<>();
		if (StringUtils.isNotBlank(region_code)) {
			retMap = platManageService.getRegionListMap(region_code);
		} else if (StringUtils.isNotBlank(parent_code)) {
			retMap = platManageService.getRegionChildrenListMap(parent_code);
		}
		return retMap;
	}
	
	
}
