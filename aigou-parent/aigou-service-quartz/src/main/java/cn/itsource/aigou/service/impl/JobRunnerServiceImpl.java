package cn.itsource.aigou.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itsource.aigou.facade.PlatManageService;
import cn.itsource.aigou.facade.UserCenterService;
import cn.itsource.aigou.facade.query.QuartzJobInfo;
import cn.itsource.aigou.service.IJobRunnerService;

@Service
public class JobRunnerServiceImpl implements IJobRunnerService {

	@Reference
	private UserCenterService userCenterService;
	
	@Reference
	private PlatManageService platManageService;
	
	@Override
	public void orderCancelByQuartz(QuartzJobInfo info) {
		userCenterService.orderCancelByQuartz(info);
	}

	@Override
	public void orderConfirmByQuartz(QuartzJobInfo info) {
		userCenterService.orderConfirmByQuartz(info);
	}

	@Override
	public void seckillPreHandle(QuartzJobInfo info) {
		platManageService.seckillPreHandle(info);
	}

	@Override
	public void seckillBeginHandle(QuartzJobInfo info) {
		platManageService.seckillBeginHandle(info);
	}

	@Override
	public void seckillEndHandle(QuartzJobInfo info) {
		platManageService.seckillEndHandle(info);
	}

	@Override
	public void seckillExpireResult(QuartzJobInfo info) {
		platManageService.seckillExpireResult(info);
	}

}
