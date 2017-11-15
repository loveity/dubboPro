package cn.itsource.aigou.facade;


import java.util.List;
import java.util.Map;

import cn.itsource.aigou.core.domain.Order;
import cn.itsource.aigou.core.domain.PayBill;
import cn.itsource.aigou.core.domain.Sku;
import cn.itsource.aigou.core.domain.Sso;
import cn.itsource.aigou.core.domain.VipAddress;
import cn.itsource.aigou.core.exception.BisException;
import cn.itsource.aigou.core.utils.Page;
import cn.itsource.aigou.core.utils.Ret;
import cn.itsource.aigou.facade.query.OrderQuery;
import cn.itsource.aigou.facade.query.QuartzJobInfo;

public interface UserCenterService {
	/**
	 * 获取指定ID的登录用户
	 * @param id
	 * @return
	 */
	Sso getSsoUser(Long id);

	/**
	 * 通过手机号码获取登录用户
	 * @param phone
	 * @return
	 */
	Sso getSsoByPhone(String phone);

	/**
	 * 通过手机号码注册用户
	 * @param phone 手机号
	 * @param password 密码
	 * @param smsCaptcha 短信验证码
	 * @return
	 */
	Ret regUserByPhone(String phone, String password, String smsCaptcha);

	/**
	 * 用户名和密码登录
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 */
	Ret login(String username, String password);

	/**
	 * 添加到购物车
	 * @param ssoId
	 * @param skuId
	 * @param number
	 */
	void cartAdd(Long ssoId,Long skuId, Integer number);

	/**
	 * 用户删除购物车
	 * @param ssoId
	 * @param cartIdArr
	 */
	void cartDel(Long ssoId, Long[] cartIdArr);

	/**
	 * 修改购物车条目的数量
	 * @param ssoId 
	 * @param cartId
	 * @param number
	 */
	Sku cartChangeNumber(Long ssoId, Long cartId, Integer number);

	/**
	 * 更新购物车选中数据
	 * @param ssoId
	 * @param cartIdArr
	 */
	void cartSelect(Long ssoId, Long[] cartIdArr);

	/**
	 * 马上购买
	 * @param ssoId
	 * @param skuId
	 * @param number
	 */
	void cartQuickBuy(Long ssoId, Long skuId, Integer number);

	/**
	 * 统计用户当前购物车勾选的情况
	 * @param ssoId
	 * @return 
	 *  goodsNumber
	 *  goodsTotalPrice
	 *  
	 *  selectedGoodsNumber
	 *  selectedGoodsTotalPrice
	 */
	Map<String, Object> cartCalculate(Long ssoId);

	/**
	 * 获取用户购物车的情况
	 * @param ssoId
	 * @return
	 *  goodsNumber
	 *  goodsTotalPrice
	 *  
	 *  selectedGoodsNumber
	 *  selectedGoodsTotalPrice
	 *  
	 *  data : List-VipCart
	 */
	Map<String, Object> cartInfo(Long ssoId);
	
	/**
	 * 获取用户购物车选中情况
	 * @param ssoId
	 * @return
	 *  goodsNumber
	 *  goodsTotalPrice
	 *  
	 *  selectedGoodsNumber
	 *  selectedGoodsTotalPrice
	 *  
	 *  data : List-VipCart selected
	 */
	Map<String, Object> cartSelectedInfo(Long ssoId);
	
	/**
	 * 用户添加/修改地址
	 * @param address
	 * @return 存储后的地址
	 */
	VipAddress vipAdrressStore(VipAddress address);
	
	/**
	 * 通过用户地址id获取
	 * @param id
	 * @return
	 */
	VipAddress vipAddressGet(Long id);
	
	/**
	 * 获取用户所有收货地址
	 * @param ssoId
	 * @return
	 */
	List<VipAddress> vipAddressesGets(Long ssoId);
	
	/**
	 * 删除用户地址
	 * @param id
	 */
	void vipAddressRemove(Long id);
	
	/**
	 * 用户默认地址设置
	 * @param id
	 */
	void vipAddressDefaultSet(Long id);
	
	/**
	 * 获取用户默认地址
	 * @param ssoId
	 * @return
	 */
	VipAddress vipAddressDefaultGet(Long ssoId);

	/**
	 * 
	 * @param formOrder 订单数据
	 * @param addressId 收货地址id
	 * @param billId 发票id
	 * @return
	 */
	Ret orderCreate(Order formOrder, Long addressId, Long billId);

	/**
	 * 通知修改订单状态
	 * @param payBill
	 */
	void notifyOrderPayed(PayBill payBill);

	/**
	 * 查询订单分页
	 * @param query
	 * @return
	 */
	Page<Order> orderQuery(OrderQuery query);

	/**
	 * 通过ssoId查询订单数量
	 * @param ssoId 用户id
	 * @param year 年份
	 * @return
	 * Map: allCount, waitPay, waitSend, waitTake, waitComment
	 */
	Map<String, Integer> orderStatisBySso(Long ssoId,Integer year);

	/**
	 * 通过id获取订单
	 * @param id
	 * @return
	 */
	Order orderGet(Long id);
	
	/**
	 * 通过订单号查询订单
	 * @param orderSn
	 * @return
	 */
	Order orderGetBySn(String orderSn);

	/**
	 * 用户取消订单
	 * @param ssoId
	 * @param orderId
	 * @return
	 */
	void orderCancel(Long ssoId, Long orderId) throws BisException;
	
	/**
	 * 用户确认收货
	 * @param ssoId
	 * @param orderId
	 * @return
	 */
	void orderConfirmFinish(Long ssoId, Long orderId) throws BisException;

	/**
	 * @param ssoId
	 * @param orderIdArr
	 */
	void orderDelete(Long ssoId,Long[] orderIdArr);

	/**
	 * 定时器取消订单
	 * @param info
	 */
	void orderCancelByQuartz(QuartzJobInfo info);

	/**
	 * 定时器确认订单
	 * @param info
	 */
	void orderConfirmByQuartz(QuartzJobInfo info);

	/**
	 * 库存中心发货
	 * @param orderIdArr 订单ID集合 
	 * @param shipStore 快递服务商id
	 * @param shipSn 快递单号
	 */
	void orderSendShip(Long[] orderIdArr,Long shipStore,String shipSn) throws BisException;
	
}
