package cn.itsource.aigou.service;

import java.util.List;

import cn.itsource.aigou.core.common.base.IBaseService;
import cn.itsource.aigou.core.domain.Seckill;
import cn.itsource.aigou.core.domain.SeckillSku;
import cn.itsource.aigou.facade.query.QuartzJobInfo;

public interface ISeckillService extends IBaseService<Seckill> {

	/**
	 * 存储
	 * @param seckill
	 */
	void saveOrUpdate(Seckill seckill);

	/**
	 * 根据秒杀活动id获取单品列表
	 * @param seckillId
	 * @return
	 */
	List<SeckillSku> getSkuList(Long seckillId);

	/**
	 * 秒杀活动发布
	 * @param seckillIdArr
	 */
	void publish(Long[] seckillIdArr);

	/**
	 * 秒杀活动预处理
	 * @param info
	 */
	void preHandle(QuartzJobInfo info);

	/**
	 * 秒杀活动预处理
	 * @param info
	 */
	void beginHandle(QuartzJobInfo info);

	/**
	 * 秒杀活动预处理
	 * @param info
	 */
	void endHandle(QuartzJobInfo info);

	/**
	 * 秒杀结果确认过期处理
	 * @param info
	 */
	void expireResult(QuartzJobInfo info);

	/**
	 * 抢购成功后进入持久化处理队列
	 * @param ssoId
	 * @param seckillSkuId
	 */
	boolean inHitQueue(Long ssoId, Long seckillSkuId);
	
}
