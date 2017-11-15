package cn.itsource.aigou.facade;

import cn.itsource.aigou.core.exception.BisException;
import cn.itsource.aigou.facade.query.QuartzJobInfo;
/**
 * 统一定时任务服务
 * @author nixianhua
 *
 */
public interface QuartzService {
	/**
	 * 添加定时任务
	 * @param info 任务信息
	 */
	void addJob(QuartzJobInfo info) throws BisException;

	/**
	 * 删除定时任务
	 * @param string
	 */
	void delJob(String string);
	
}
