package cn.itsource.aigou.core.mapper;

import org.apache.ibatis.annotations.Param;

import cn.itsource.aigou.core.domain.Order;

public interface OrderMapper extends BaseMapper<Order> {

	/**
	 * 统计订单数
	 * @param ssoId 用户id
	 * @param state 订单状态
	 * @return
	 */
	Integer querySta(@Param("ssoId") Long ssoId, @Param("state") Byte state);

	/**
	 * 统计待评价订单
	 * @param ssoId 用户id
	 * @return
	 */
	Integer queryStaComment(@Param("ssoId") Long ssoId);

	/**
	 * 通过订单号查找订单
	 * @param orderSn
	 * @return
	 */
	Order getBySn(String orderSn);
	
	/**
	 * 通过订单id获取订单
	 * @param id
	 * @return
	 */
	Order getById(Long id);

	/**
	 * 批量删除用户订单
	 * @param ssoId
	 * @param orderIdArr
	 */
	void logicDeleteBatch(@Param("ssoId") Long ssoId,@Param("orderIdArr") Long[] orderIdArr);
	
}