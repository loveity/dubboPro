package cn.itsource.aigou.service;

import java.util.Map;

import cn.itsource.aigou.core.common.base.IBaseService;
import cn.itsource.aigou.core.domain.Order;
import cn.itsource.aigou.core.domain.PayBill;
import cn.itsource.aigou.core.utils.Page;
import cn.itsource.aigou.core.utils.Ret;
import cn.itsource.aigou.facade.query.OrderQuery;

public interface IOrderService extends IBaseService<Order> {

	/**
	 * 
	 * @param formOrder 订单数据
	 * @param addressId 收货地址id
	 * @param billId 发票id
	 * @return
	 */
	Ret create(Order formOrder, Long addressId, Long billId);

	/**
	 * 通知订单支付成功
	 * @param payBill
	 */
	void notifyPayed(PayBill payBill);

	/**
	 * 通过ssoId查询订单数量
	 * @param ssoId 用户id
	 * @param year 年份
	 * @return
	 * Map: allCount, waitPay, waitSend, waitTake, waitComment
	 */
	Map<String, Integer> statisBySso(Long ssoId, Integer year);

	/**
	 * 通过订单号查询订单
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
	 * 用户取消订单
	 * @param ssoId
	 * @param orderId
	 */
	void cancelBySso(Long ssoId, Long orderId);
	
	/**
	 * 取消订单
	 * @param orderId
	 */
	void cancel(Long orderId);

	/**
	 * 订单取消失败
	 * @param oldBisKey
	 */
	void cancelFailed(Long oldBisKey);

	/**
	 * 订单取消成功
	 * @param oldBisKey
	 */
	void cancelFinished(Long oldBisKey);

	/**
	 * 用户批量删除订单
	 * @param orderIdArr
	 * @param ssoId
	 */
	void deleteBatch(Long ssoId,Long[] orderIdArr);

	/**
	 * 发货确认
	 * @param orderIdArr
	 * @param shipStore 快递服务商ID
	 * @param shipSn 快递单号
	 */
	void sendShip(Long[] orderIdArr,Long shipStore,String shipSn);

	/**
	 * 系统确认收货（订单完成）
	 * @param orderId
	 */
	void confirmFnish(long orderId);

	/**
	 * 用户确认收货（订单完成）
	 * @param ssoId
	 * @param orderId
	 */
	void confirmFinishBySso(Long ssoId, Long orderId);

	/**
	 * 由支付过期定时任务取消
	 * @param orderId
	 */
	void cancelByQuartz(long orderId);

	/**
	 * 由确认收货过期定时任务确认
	 * @param orderId
	 */
	void confirmFnishByQuartz(long orderId);
	
}
