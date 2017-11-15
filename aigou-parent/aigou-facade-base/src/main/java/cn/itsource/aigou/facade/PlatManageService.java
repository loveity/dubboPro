package cn.itsource.aigou.facade;

import java.util.List;
import java.util.Map;

import cn.itsource.aigou.core.domain.Employee;
import cn.itsource.aigou.core.domain.Seckill;
import cn.itsource.aigou.core.domain.SeckillSku;
import cn.itsource.aigou.core.utils.Page;
import cn.itsource.aigou.facade.query.QuartzJobInfo;
import cn.itsource.aigou.facade.query.SeckillQuery;

public interface PlatManageService {
	/**
	 * 获取指定ID的员工
	 * @param id
	 * @return
	 */
	Employee getEmployee(Long id);
	
	/**
	 * 通过regionCode获取展示地区列表
	 * @param regionCode
	 * @return
	 */
	Map<String, Object> getRegionListMap(String regionCode);
	
	/**
	 * 通过父级地区编码(parentCode)获取下级地区的数据
	 * @param parentCode
	 * @return
	 */
	Map<String, Object> getRegionChildrenListMap(String parentCode);
	
	
	/**
	 * 通过地区编码获取区域全名
	 * @param regionCode 510101
	 * @return
	 */
	String getRegionFullName(String regionCode);

	/**
	 * 获取秒杀活动列表
	 * @param qObject
	 * @return
	 */
	Page<Seckill> seckillPage(SeckillQuery qObject);

	/**
	 * 获取秒杀活动数据
	 * @param id
	 * @return
	 */
	Seckill seckillGet(Long id);

	/**
	 * 保存秒杀活动数据
	 * @param seckill
	 */
	void seckillStore(Seckill seckill);

	/**
	 * 通过秒杀活动ID获取单品列表
	 * @param seckillId
	 * @return
	 */
	List<SeckillSku> seckillGetSkus(Long seckillId);

	/**
	 * 秒杀活动发布
	 * @param seckillIdArr
	 */
	void seckillPublish(Long[] seckillIdArr);

	/**
	 * 秒杀活动预处理
	 * @param info
	 */
	void seckillPreHandle(QuartzJobInfo info);

	/**
	 * 秒杀活动开始处理
	 * @param info
	 */
	void seckillBeginHandle(QuartzJobInfo info);

	/**
	 * 秒杀任务结束处理
	 * @param info
	 */
	void seckillEndHandle(QuartzJobInfo info);

	/**
	 * 秒杀结果确认过期处理
	 * @param info
	 */
	void seckillExpireResult(QuartzJobInfo info);

	/**
	 * 成功抢购进入成功队列
	 * @param ssoId
	 * @param skuId
	 */
	boolean inSechitQueue(Long ssoId, Long skuId);
	
}
