package cn.itsource.aigou.service;

import java.util.Map;

import cn.itsource.aigou.core.common.base.IBaseService;
import cn.itsource.aigou.core.domain.Order;
import cn.itsource.aigou.core.domain.PayAccountFlow;
import cn.itsource.aigou.core.domain.PayBill;

public interface IPayBillService extends IBaseService<PayBill> {

	/**
	 * 创建商品订单的支付单
	 * @param order
	 * @return
	 */
	PayBill createProductOrderBill(Order order);

	/**
	 * 获取指定支付单号的支付单
	 * @param paySn
	 * @return
	 */
	PayBill getPayBill(String paySn);

	/**
	 * 
	 * @param unionPaySn 统一支付单号
	 * @param totalAmount 支付金额（元）
	 * @param tradeNo 第三方交易流水号
	 * @param parameterMap 报文数据
	 * @return
	 */
	PayBill notifyBillPayed(String unionPaySn, String totalAmount, String tradeNo, Object parameterMap);

	/**
	 * 通过支付单创建支付流水
	 * @param payBill
	 * @return
	 */
	PayAccountFlow createPayAccountFlow(PayBill payBill);
	/**
	 * 
	 * @param bisType 业务类型
	 * @param bisKey 业务键
	 * @return 统一支付码
	 */
	String getUnionPaySnByBusinessKey(int bisType, long bisKey);

	/**
	 * 主动查询支付宝支付情况，并返回支付单最新数据
	 * @param payBill
	 * @return
	 */
	PayBill dealBillFromAlipay(PayBill payBill);

	/**
	 * 通过业务类型和业务键查询支付单
	 * @param bisType
	 * @param bisKey
	 * @return
	 */
	PayBill getByBusinessKey(byte bisType, long bisKey);
	
}
