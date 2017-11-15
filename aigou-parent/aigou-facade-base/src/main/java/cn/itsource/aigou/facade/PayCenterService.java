package cn.itsource.aigou.facade;

import cn.itsource.aigou.core.domain.Order;
import cn.itsource.aigou.core.domain.PayBill;
import cn.itsource.aigou.core.domain.Sso;

import java.util.Map;

public interface PayCenterService {
	/**
	 * 获取指定ID的支付单
	 * @param id
	 * @return
	 */
	PayBill getPayBill(Long id);
	/**
	 * 获取指定支付单号的支付单
	 * @param paySn
	 * @return
	 */
	PayBill getPayBillBySn(String paySn);

	/**
	 * 用户注册成功后，同步创建用户账户
	 * @param sso
	 */
	void createVipAccount(Sso sso);

	/**
	 * 通过商品订单创建统一支付单
	 * @param order
	 * @return
	 */
	PayBill orderPayBillCreate(Order order);
	
	/**
	 * 
	 * @param unionPaySn 统一支付单号
	 * @param totalAmount 支付金额（元）
	 * @param tradeNo 第三方交易流水号
	 * @param parameterMap 报文数据
	 * @return
	 */
	PayBill notifyBillPayed(String unionPaySn, String totalAmount, String tradeNo,
			Object parameterMap);
	
	/**
	 * 
	 * @param bisType 业务类型
	 * @param bisKey 业务键
	 * @return 统一支付码
	 */
	String getUnionPaySnByBusinessKey(int bisType, long bisKey);
	
	/**
	 * 主动查询支付单在支付宝的支付情况，并返回查询后的支付单
	 * @param payBill
	 * @return
	 */
	PayBill dealBillFromAlipay(PayBill payBill);
	
	/**
	 * 生成支付宝网页支付数据包
	 * @param payBill
	 * @return
	 */
	String generateAlipayPageData(PayBill payBill);
	/**
	 * 验证支付宝支付通知的签名
	 * @param params
	 * @param aliPubKey
	 * @param charset
	 * @param signType
	 * @return
	 */
	boolean checkAlipaySignature(Map<String, String> params, String aliPubKey, String charset, String signType);
	/**
	 * 验证支付宝通知报文中关于参数的合法性
	 * @param appId
	 * @return
	 */
	boolean validateNotifyParams(String appId);
	
}
