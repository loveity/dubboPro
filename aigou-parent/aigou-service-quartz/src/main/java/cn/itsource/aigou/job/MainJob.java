package cn.itsource.aigou.job;

import java.util.Date;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cn.itsource.aigou.core.consts.bis.JobTypeConsts;
import cn.itsource.aigou.facade.query.QuartzJobInfo;
import cn.itsource.aigou.service.impl.JobRunnerService;
/**
 * 处理定时调度任务的统一入口
 * @author nixianhua
 *
 */
public class MainJob implements Job{

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
		JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();  
		QuartzJobInfo info = (QuartzJobInfo) jobDataMap.get("params");
		
		switch (info.getType()) {
			case JobTypeConsts.WAIT_ORDER_PAY_CANCEL_JOB:
				JobRunnerService.cancelOrder(info);
				break;
			case JobTypeConsts.WAIT_ORDER_CONFIRM_SHIP_JOB:
				JobRunnerService.confirmOrder(info);
				break;
			case JobTypeConsts.SECKILL_PRE_JOB:
				JobRunnerService.preHandleSeckill(info);
				break;
			case JobTypeConsts.SECKILL_BEGIN_JOB:
				JobRunnerService.beginHandleSeckill(info);
				break;
			case JobTypeConsts.SECKILL_END_JOB:
				JobRunnerService.endHandleSeckill(info);
				break;
			case JobTypeConsts.SECKILL_CONFIRM_EXPIRE_JOB:
				JobRunnerService.expireSeckillResult(info);
				break;
			default:
				break;
		}
	}

}