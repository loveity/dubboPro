package cn.itsource.aigou.service;

import cn.itsource.aigou.core.common.base.IBaseService;
import cn.itsource.aigou.core.domain.Sso;
import cn.itsource.aigou.core.domain.VipBase;

public interface IVipBaseService extends IBaseService<VipBase> {

	/**
	 * 注册成功后，创建会员基本信息记录
	 * @param sso
	 * @param channel 注册渠道
	 */
	void create(Sso sso,Byte channel);
	
}
