package cn.itsource.aigou.web.controller;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itsource.aigou.facade.CommonService;

@Service
public class ControllerHelper implements IControllerHelper{
	@Reference
	private CommonService commonService;

	public String getSsoId(Long ssoId) {
		return commonService.getRedisSsoId(ssoId);
	}

	public void refreshRedisSsoId(Long ssoId) {
		commonService.refreshRedisSsoId(ssoId);
	}
	
	
	
	
}
