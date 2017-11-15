package cn.itsource.aigou.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.consts.GlobalSettingNames;
import cn.itsource.aigou.core.consts.ICodes;
import cn.itsource.aigou.core.consts.bis.BooleanConsts;
import cn.itsource.aigou.core.consts.bis.JobTypeConsts;
import cn.itsource.aigou.core.consts.bis.OrderStateConsts;
import cn.itsource.aigou.core.consts.bis.PayBillBusinessTypeConsts;
import cn.itsource.aigou.core.consts.msg.UserCenterMsgConsts;
import cn.itsource.aigou.core.domain.Order;
import cn.itsource.aigou.core.domain.OrderAddress;
import cn.itsource.aigou.core.domain.OrderDetail;
import cn.itsource.aigou.core.domain.PayBill;
import cn.itsource.aigou.core.domain.Sku;
import cn.itsource.aigou.core.domain.SmsCode;
import cn.itsource.aigou.core.domain.VipAddress;
import cn.itsource.aigou.core.domain.VipCart;
import cn.itsource.aigou.core.exception.BisException;
import cn.itsource.aigou.core.mapper.OrderMapper;
import cn.itsource.aigou.core.utils.CodeGenerateUtils;
import cn.itsource.aigou.core.utils.GlobalSetting;
import cn.itsource.aigou.core.utils.Page;
import cn.itsource.aigou.core.utils.Ret;
import cn.itsource.aigou.facade.ProductCenterService;
import cn.itsource.aigou.facade.QuartzService;
import cn.itsource.aigou.facade.query.OrderQuery;
import cn.itsource.aigou.facade.query.QuartzJobInfo;
import cn.itsource.aigou.service.IOrderAddressService;
import cn.itsource.aigou.service.IOrderDetailService;
import cn.itsource.aigou.service.IOrderService;
import cn.itsource.aigou.service.IVipAddressService;
import cn.itsource.aigou.service.IVipCartService;
/**
 * 订单处理主逻辑
 * @author nixianhua
 *
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order> implements IOrderService {
	@Reference
	private ProductCenterService productCenterService;
	
	@Reference
	private QuartzService quartzService;

	@Autowired
	private IVipCartService cartService;
	@Autowired
	private OrderMapper mapper;
	@Autowired
	private IVipAddressService addressService;
	@Autowired
	private IOrderAddressService orderAddressService;
	@Autowired
	private IOrderDetailService orderDetailService;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	@Qualifier("payCancelApplyQueueDestination")
	private Destination payCancelApplyQueueDestination;

	@Override
	protected BaseMapper<Order> getMapper() {
		return mapper;
	}

	@Override
	public Ret create(Order formOrder, Long addressId, Long billId) {
		Integer totalMoney = 0;// 订单所有商品的总价
		StringBuilder digest = new StringBuilder();// 订单摘要

		// 获取用户购物车情况:处理库存、销量、计算订单总价及摘要
		List<VipCart> carts = cartService.getCarts(formOrder.getSsoId());
		for (VipCart vipCart : carts) {
			if (vipCart.getSelected() == BooleanConsts.YES) {
				// 当前sku
				Sku sku = vipCart.getSku();
				// 当前可用库存
				Integer availableStock = sku.getAvailableStock();
				// 购买数量
				Integer amount = vipCart.getAmount();
				// 库存不足
				if (amount > availableStock) {
					return Ret.me().setSuccess(false).setCode(UserCenterMsgConsts.ORDER_STOCK_NOT_ENOUGH);
				}
				// 库存充足，扣除库存
				sku.setAvailableStock(sku.getAvailableStock() - amount);
				sku.setFrozenStock(sku.getFrozenStock() + amount);
				productCenterService.updateSku(sku);

				// TODO .增加商品的销量

				// 获取商品订单总价
				totalMoney += vipCart.getAmount() * vipCart.getSku().getPrice();

				// 获取摘要
				digest.append(vipCart.getName());
				String skuProperties = vipCart.getSkuProperties();
				digest.append("-").append(skuProperties);
				digest.append("×").append(vipCart.getAmount()).append(",");
			}
		}

		String orderSn = CodeGenerateUtils.generateOrderSn(formOrder.getSsoId());// 订单号
		// TODO .计算运费
		Integer carrageFee = 0; // 运费
		// TODO .计算优惠金额（会员折扣）
		Integer discountMoney = 0; // 优惠金额
		// TODO .计算优惠券金额（并使用优惠券）
		Long couponId = null; // 优惠券ID
		Integer couponMoney = 0;// 优惠券金额
		// TODO .计算促销活动金额（并记录参与促销活动记录）
		Long promotionId = null;// 活动促销id
		Integer promotionMoney = 0;// 活动促销金额

		// 计算支付截止时间
		BigDecimal hours = new BigDecimal(GlobalSetting.get(GlobalSettingNames.SYSTEM_PAYTIME_LIMIT_HOURS));
		BigDecimal millsExpires = hours.multiply(new BigDecimal(3600000));
		long lastPayTime = (millsExpires.add(new BigDecimal(System.currentTimeMillis()))).longValue();

		// 实际金额 : 商品总价totalMoney + 运费carrageFee
		// - 优惠金额discountMoney - 优惠券金额 couponMoney- 活动促销金额promotionMoney
		Integer realMoney = totalMoney + carrageFee - discountMoney - couponMoney - promotionMoney;

		// 设置订单信息
		formOrder.setOrderSn(orderSn);
		formOrder.setStoreId(1L);
		formOrder.setStoreName("爱购网官方一号店");
		formOrder.setState(OrderStateConsts.WAIT_PAY);

		formOrder.setCarriageFee(carrageFee);
		formOrder.setTotalMoney(totalMoney);
		formOrder.setDiscountMoney(discountMoney);
		formOrder.setRealMoney(realMoney);
		formOrder.setPayMoney(0);// 已支付 0
		formOrder.setCouponId(couponId);
		formOrder.setCouponMoney(couponMoney);
		formOrder.setPromotionId(promotionId);
		formOrder.setPromotionMoney(promotionMoney);

		formOrder.setCommentStatus((byte) BooleanConsts.NO);
		formOrder.setDigest(digest.toString());

		formOrder.setCreateTime(System.currentTimeMillis());
		formOrder.setUpdateTime(formOrder.getCreateTime());

		formOrder.setLastPayTime(lastPayTime);

		this.savePart(formOrder);

		// 创建订单关联的收货地址
		VipAddress address = addressService.get(addressId);
		OrderAddress orderAddress = new OrderAddress();
		BeanUtils.copyProperties(address, orderAddress);
		orderAddress.setOrderId(formOrder.getId());
		orderAddress.setOrderSn(formOrder.getOrderSn());
		orderAddressService.savePart(orderAddress);
		// 创建订单关联的订单明细
		for (VipCart vipCart : carts) {
			if (vipCart.getSelected() == BooleanConsts.YES) {
				// 当前sku
				Sku sku = vipCart.getSku();
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setAmount(vipCart.getAmount());
				orderDetail.setCreateTime(System.currentTimeMillis());
				orderDetail.setMarketPrice(sku.getMarketPrice());
				orderDetail.setName(vipCart.getName());
				orderDetail.setOrderId(formOrder.getId());
				orderDetail.setPrice(sku.getPrice());
				orderDetail.setProductId(vipCart.getProductId());
				orderDetail.setSkuId(vipCart.getSkuId());
				orderDetail.setSkuMainPic(vipCart.getSkuMainPic());
				orderDetail.setSkuProperties(vipCart.getSkuProperties());
				orderDetail.setTotalMoney(vipCart.getAmount() * sku.getPrice());
				orderDetail.setUpdateTime(orderDetail.getCreateTime());
				orderDetailService.savePart(orderDetail);
			}
		}
		// 删除购物车已购买部分的数据
		cartService.clearBuy(formOrder.getSsoId());
		
		// 取消订单支付倒计时任务
		Map<String, Object> jobParams = new HashMap<>();
		jobParams.put("orderId", formOrder.getId());
		QuartzJobInfo info = new QuartzJobInfo();
		info.setFireDate(new Date(lastPayTime));
		info.setParams(jobParams);
		info.setJobName("CANCEL_ORDER_TASK_"+formOrder.getId());
		info.setType(JobTypeConsts.WAIT_ORDER_PAY_CANCEL_JOB);
		quartzService.addJob(info);
		
		// 返回创建好的订单
		return Ret.me().setData(formOrder);
	}

	@Override
	public void notifyPayed(PayBill payBill) {
		// 更新订单的 状态state、实付金额payMoney、支付时间payTime
		Order order = this.get(payBill.getBusinessKey());
		if (order == null) {
			throw BisException.me();
		}
		
		//状态必须为待支付状态，通知才有效
		if(order.getState() != OrderStateConsts.WAIT_PAY){
			return ;
		}
		
		//修改订单状态为待发货
		order.setState(OrderStateConsts.WAIT_SHIP_SEND);
		order.setPayMoney(payBill.getMoney());
		order.setPayTime(System.currentTimeMillis());
		order.setUpdateTime(System.currentTimeMillis());
		this.updatePart(order);
		
		//删除待支付订单自动取消定时任务
		quartzService.delJob("CANCEL_ORDER_TASK_"+order.getId());
	}


	@Override
	public Map<String, Integer> statisBySso(Long ssoId, Integer year) {
		Map<String, Integer> map = new HashMap<>();
		// allCount
		Integer allCount = mapper.querySta(ssoId, null);
		// 待支付waitPay
		Integer waitPay = mapper.querySta(ssoId, OrderStateConsts.WAIT_PAY);
		// waitSend
		Integer waitSend = mapper.querySta(ssoId, OrderStateConsts.WAIT_SHIP_SEND);
		// waitTake
		Integer waitTake = mapper.querySta(ssoId, OrderStateConsts.WAIT_SHIP_TAKE);
		// waitComment
		Integer waitComment = mapper.queryStaComment(ssoId);

		// cancelApply
		Integer cancelApply = mapper.querySta(ssoId, OrderStateConsts.CANCEL_APPLY);

		map.put("allCount", allCount);
		map.put("waitPay", waitPay);
		map.put("waitSend", waitSend + cancelApply);
		map.put("waitTake", waitTake);
		map.put("waitComment", waitComment);

		return map;
	}

	@Override
	public Order getBySn(String orderSn) {
		return mapper.getBySn(orderSn);
	}

	@Override
	public Order getById(Long id) {
		return mapper.getById(id);
	}
	
	@Override
	public void cancel(Long orderId) {
		Order order = mapper.get(orderId);
		this.cancelOrder(order);
	}
	
	@Override
	public void cancelBySso(Long ssoId, Long orderId) {
		Order order = mapper.get(orderId);
		// 检查是否是当前用户的订单
		if (order.getSsoId().longValue() != ssoId) {
			throw BisException.me().setCode(ICodes.ILLEGAL_ACCESS);
		}
		this.cancelOrder(order);
		//删除待支付订单自动取消定时任务
		quartzService.delJob("CANCEL_ORDER_TASK_"+order.getId());
	}

	/**
	 * 取消订单具体业务逻辑
	 * @param order
	 */
	private void cancelOrder(Order order) {
		// 检查订单状态（是否可被取消），当订单状态为待付款、待发货状态可取消
		if (OrderStateConsts.WAIT_PAY != order.getState() && OrderStateConsts.WAIT_SHIP_SEND != order.getState()) {
			throw BisException.me().setCode(UserCenterMsgConsts.ORDER_CANCEL_FORBIDDEN);
		}
		
		// 修改订单状态 -> 取消订单申请中
		order.setState(OrderStateConsts.CANCEL_APPLY);
		this.updatePart(order);
		
		//通过队列处理(消息队列解耦)
		//（个人中心）生产支付取消消息-> 队列 -> 
		jmsTemplate.send(payCancelApplyQueueDestination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				MapMessage mapMessage = session.createMapMessage();
				mapMessage.setByte("oldBisType", PayBillBusinessTypeConsts.ORDER_PRODUCT);
				mapMessage.setByte("newBisType", PayBillBusinessTypeConsts.ORDER_CANCEL);
				mapMessage.setLong("oldBisKey", order.getId());
				return mapMessage;
			}
		});
	}

	@Override
	public void cancelFailed(Long orderId) {
		Order order = this.get(orderId);
		//恢复订单(恢复订单状态)
		order.setState(OrderStateConsts.WAIT_SHIP_SEND);
		this.updatePart(order);
		//TODO .发送短信通知
		System.out.println("您的订单取消失败，您可尝试重新取消，或联系客服");
	}

	@Override
	public void cancelFinished(Long orderId) {
		Order order = this.getById(orderId);
		//修改订单状态
		order.setState(OrderStateConsts.CLOSED);
		this.updatePart(order);
		//TODO .（通知库存系统）恢复商品库存
		List<OrderDetail> detailList = order.getDetailList();
		for (OrderDetail orderDetail : detailList) {
			Long skuId = orderDetail.getSkuId();
			Integer amount = orderDetail.getAmount();
			//恢复库存
			productCenterService.recoverSkuStock(skuId,amount);
		}
	}

	@Override
	public void deleteBatch(Long ssoId, Long[] orderIdArr) {
		mapper.logicDeleteBatch(ssoId,orderIdArr);
	}

	@Override
	public void sendShip(Long[] orderIdArr,Long shipStore,String shipSn) {
		if(orderIdArr==null || orderIdArr.length==0) {
			return;
		}
		
		for (Long orderId : orderIdArr) {
			Order order = this.getById(orderId);
			if(order.getState() != OrderStateConsts.WAIT_SHIP_SEND){
				throw BisException.me().setCode(ICodes.ILLEGAL_ACCESS);
			}
			
			List<OrderDetail> detailList = order.getDetailList();
			for (OrderDetail orderDetail : detailList) {
				Long skuId = orderDetail.getSkuId();
				Integer amount = orderDetail.getAmount();
				productCenterService.outboudSku(skuId,amount);
			}
			//修改订单状态和配送信息
			BigDecimal days = new BigDecimal(GlobalSetting.get(GlobalSettingNames.SYSTEM_CONFIRM_SHIP_DAYS));
//			BigDecimal millsExpires = days.multiply(new BigDecimal(24*3600000));
			BigDecimal millsExpires = days.multiply(new BigDecimal(3600000));//测试用
			long lastConfirmTime = (millsExpires.add(new BigDecimal(System.currentTimeMillis()))).longValue();
			
			order.setLastConfirmShipTime(lastConfirmTime);
			order.setState(OrderStateConsts.WAIT_SHIP_TAKE);
			order.setShipStore(shipStore);
			order.setShipSn(shipSn);
			order.setShipSendTime(System.currentTimeMillis());
			order.setUpdateTime(System.currentTimeMillis());
			this.updatePart(order);
			
			//添加自动确认收货任务
			
			Map<String, Object> jobParams = new HashMap<>();
			jobParams.put("orderId", order.getId());
			QuartzJobInfo info = new QuartzJobInfo();
			info.setFireDate(new Date(lastConfirmTime));
			info.setParams(jobParams);
			info.setJobName("CONFIRM_ORDER_TASK_"+order.getId());
			info.setType(JobTypeConsts.WAIT_ORDER_CONFIRM_SHIP_JOB);
			quartzService.addJob(info);
			
			//TODO .可选择在发货后短信通知用户发货信息
			
		}
	}

	@Override
	public void confirmFnish(long orderId) {
		Order order = mapper.get(orderId);
		this.confirmFinishOrder(order);
	}

	@Override
	public void confirmFinishBySso(Long ssoId, Long orderId) {
		Order order = mapper.get(orderId);
		// 检查是否是当前用户的订单
		if (order.getSsoId().longValue() != ssoId) {
			throw BisException.me().setCode(ICodes.ILLEGAL_ACCESS);
		}
		this.confirmFinishOrder(order);
		//删除自动确认订单收货定时任务
		quartzService.delJob("CONFIRM_ORDER_TASK_"+order.getId());
	}
	
	/**
	 * 统一确认订单收货逻辑
	 * @param order
	 */
	private void confirmFinishOrder(Order order){
		if(order.getState() != OrderStateConsts.WAIT_SHIP_TAKE){
			throw BisException.me().setCode(ICodes.ILLEGAL_ACCESS);
		}
		
		order.setFinishedTime(System.currentTimeMillis());
		order.setState(OrderStateConsts.FINISHED);
		
		this.updatePart(order);
	}

	@Override
	public void cancelByQuartz(long orderId) {
		Order order = mapper.get(orderId);
		//如果状态已经不是待支付，则不处理
		if(order.getState() != OrderStateConsts.WAIT_PAY){
			return ;
		}
		this.cancelOrder(order);
	}

	@Override
	public void confirmFnishByQuartz(long orderId) {
		Order order = mapper.get(orderId);
		//如果状态已经不是待收货，则不处理
		if(order.getState() != OrderStateConsts.WAIT_SHIP_TAKE){
			return ;
		}
		this.confirmFinishOrder(order);
	}
}
