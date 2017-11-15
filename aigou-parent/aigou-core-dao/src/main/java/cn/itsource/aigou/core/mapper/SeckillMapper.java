package cn.itsource.aigou.core.mapper;

import java.util.List;

import cn.itsource.aigou.core.domain.Seckill;
import cn.itsource.aigou.core.domain.SeckillSku;

public interface SeckillMapper extends BaseMapper<Seckill> {

	/**
	 * 批量添加活动单品列表
	 * @param seckillSkuList
	 */
	void saveSkuList(List<SeckillSku> seckillSkuList);
	
	/**
	 * 根据秒杀活动ID删除所有单品
	 * @param seckillId
	 */
	void deleteBySecKillId(Long seckillId);

	/**
	 * 通过秒杀活动id获取单品列表
	 * @param seckillId
	 * @return
	 */
	List<SeckillSku> getSkuList(Long seckillId);

	/**
	 * 获取指定状态的抢购活动列表
	 * @param 
	 * @return
	 */
	List<Seckill> getByState(Byte state);
	
}