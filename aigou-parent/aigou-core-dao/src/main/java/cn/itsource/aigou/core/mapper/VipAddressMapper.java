package cn.itsource.aigou.core.mapper;

import java.util.List;

import cn.itsource.aigou.core.domain.VipAddress;

public interface VipAddressMapper extends BaseMapper<VipAddress> {

	/**
	 * 获取用户的所有地址
	 * @param ssoId
	 * @return
	 */
	List<VipAddress> getBySso(Long ssoId);

	/**
	 * 设置默认地址
	 * @param id
	 */
	void setDefault(Long id);

	/**
	 * 获取用户的默认地址
	 * @param ssoId
	 * @return
	 */
	VipAddress getDefault(Long ssoId);

	/**
	 * 取消用户的默认地址
	 * @param ssoId
	 */
	void cancelDefault(Long ssoId);
	
}