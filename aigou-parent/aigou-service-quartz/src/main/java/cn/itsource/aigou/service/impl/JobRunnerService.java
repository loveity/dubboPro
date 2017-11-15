package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.facade.query.QuartzJobInfo;
import cn.itsource.aigou.service.IJobRunnerService;

@Service
public class JobRunnerService {
	
	private static IJobRunnerService runService;
	
	@Autowired
	public void setRunService(IJobRunnerService runService) {
		JobRunnerService.runService = runService;
	}
	
	/**
	 * 取消订单
	 * @param info
	 */
	public static void cancelOrder(QuartzJobInfo info){
		runService.orderCancelByQuartz(info);
	}
	/**
	 * 确认订单
	 * @param info
	 */
	public static void confirmOrder(QuartzJobInfo info){
		runService.orderConfirmByQuartz(info);
	}

	/**
	 * 秒杀预处理
	 * @param info
	 */
	public static void preHandleSeckill(QuartzJobInfo info) {
		runService.seckillPreHandle(info);
	}

	/**
	 * 秒杀开始处理
	 * @param info
	 */
	public static void beginHandleSeckill(QuartzJobInfo info) {
		runService.seckillBeginHandle(info);
	}

	/**
	 * 秒杀结束处理
	 * @param info
	 */
	public static void endHandleSeckill(QuartzJobInfo info) {
		runService.seckillEndHandle(info);
	}

	/**
	 * 秒杀结果过期任务处理
	 * @param info
	 */
	public static void expireSeckillResult(QuartzJobInfo info) {
		runService.seckillExpireResult(info);
	}
	
}
