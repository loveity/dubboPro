package cn.itsource.aigou.service;

import cn.itsource.aigou.core.common.base.IBaseService;
import cn.itsource.aigou.core.domain.SeckillSku;

public interface ISeckillSkuService extends IBaseService<SeckillSku> {

	/**
	 * 用户抢购单品成功
	 * @param seckillSkuId 单品抢购id
	 * @param ssoId 用户id
	 */
	void hitSuccess(long seckillSkuId, long ssoId);

}
