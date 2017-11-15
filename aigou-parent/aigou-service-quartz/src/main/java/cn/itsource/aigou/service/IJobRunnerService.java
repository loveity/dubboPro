package cn.itsource.aigou.service;

import cn.itsource.aigou.facade.query.QuartzJobInfo;

public interface IJobRunnerService {

	void orderCancelByQuartz(QuartzJobInfo info);

	void orderConfirmByQuartz(QuartzJobInfo info);

	void seckillPreHandle(QuartzJobInfo info);

	void seckillBeginHandle(QuartzJobInfo info);

	void seckillEndHandle(QuartzJobInfo info);

	void seckillExpireResult(QuartzJobInfo info);
	
}
