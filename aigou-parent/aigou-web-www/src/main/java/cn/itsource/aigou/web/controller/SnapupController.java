package cn.itsource.aigou.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;

import cn.itsource.aigou.core.consts.ICodes;
import cn.itsource.aigou.core.exception.BisException;
import cn.itsource.aigou.core.utils.Ret;
import cn.itsource.aigou.facade.CommonService;

@Controller
public class SnapupController {

	@Reference
	private CommonService commonService;
	
	@RequestMapping("/snapup")
	public String snapup(){
		
		return "snapup";
	}
	
	@RequestMapping("/seckill/code")
	@ResponseBody
	public Ret seckillCode(Long seckillId,String secSkuIds){
		if(StringUtils.isBlank(secSkuIds)||seckillId==null){
			throw BisException.me().setCode(ICodes.ILLEGAL_ACCESS);
		}
		
		String code = commonService.redisGet("seckill-rn-" + seckillId);
		if(null==code){
			code="";
		}
		
		List<Map<String, Object>> leftCountMapList = new ArrayList<>();
		String[] secSkuIdArr = secSkuIds.trim().split(" ");
		for (String secSkuId : secSkuIdArr) {
			String num = commonService.redisGet("seckill-num-" + secSkuId);
			if(num==null){
				num = "0";
			}
			Map<String, Object> map = new HashMap<>() ;
			map.put("skuid", secSkuId);
			map.put("num", num);
			leftCountMapList.add(map);
		}
		
		Map<String, Object> retMap = new HashMap<>();
		retMap.put("seckillId", seckillId);
		retMap.put("code", code);
		retMap.put("numList", leftCountMapList);
		return Ret.me().setData(retMap);
	}
	
}
