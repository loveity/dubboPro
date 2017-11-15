package cn.itsource.aigou.service;

import java.util.List;

import cn.itsource.aigou.core.common.base.IBaseService;
import cn.itsource.aigou.core.domain.VipAddress;

public interface IVipAddressService extends IBaseService<VipAddress> {

	/**
	 * 用户添加/修改地址
	 * @param address
	 * @return 存储后的地址
	 */
	VipAddress store(VipAddress address);

	/**
	 * 获取用户所有收货地址
	 * @param ssoId
	 * @return
	 */
	List<VipAddress> getBySso(Long ssoId);

	/**
	 * 设置当前的地址为用户默认地址
	 * @param id
	 */
	void setDefault(Long id);

	/**
	 * 获取用户默认地址
	 * @param ssoId
	 * @return
	 */
	VipAddress getDefault(Long ssoId);
	
}
