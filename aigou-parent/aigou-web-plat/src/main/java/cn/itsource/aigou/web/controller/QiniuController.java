package cn.itsource.aigou.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itsource.aigou.facade.CommonService;

@Controller
public class QiniuController {
	@Reference
	private CommonService commonService;
	
	@RequestMapping("/plugin/qiniu/uptoken")
	@ResponseBody
	public Map<String, String> uptoken(){
		String token = commonService.getQiniuUpToken(null);
		Map<String, String> retMap = new HashMap<>();
		retMap.put("uptoken", token);
		return retMap;
	}
}
