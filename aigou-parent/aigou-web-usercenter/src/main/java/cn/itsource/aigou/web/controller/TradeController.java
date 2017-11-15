package cn.itsource.aigou.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.NEW;

import cn.itsource.aigou.core.consts.ConstUtils;
import cn.itsource.aigou.core.consts.ICodes;
import cn.itsource.aigou.core.consts.bis.OrderStateConsts;
import cn.itsource.aigou.core.consts.bis.PayBillBusinessTypeConsts;
import cn.itsource.aigou.core.domain.Order;
import cn.itsource.aigou.core.domain.OrderDetail;
import cn.itsource.aigou.core.exception.BisException;
import cn.itsource.aigou.core.utils.Page;
import cn.itsource.aigou.core.utils.Ret;
import cn.itsource.aigou.core.utils.StrUtils;
import cn.itsource.aigou.facade.UserCenterService;
import cn.itsource.aigou.facade.query.OrderQuery;
import cn.itsource.aigou.web.utils.SsoContext;

/**
 * 交易中心
 * @author nixianhua
 *
 */
@Controller
@RequestMapping("/trade")
public class TradeController {
	
	@Reference
	private UserCenterService userCenterService;
	
	/**
	 * 我的收藏
	 * @return
	 */
	@RequestMapping("/collect")
	public String collect(){
		//TODO 我的收藏页面
		return "trade/collect";
	}
	
	/**
	 * 我的评价
	 * @return
	 */
	@RequestMapping("/comment")
	public String comment(){
		//TODO 我的评论页面
		return "trade/comment";
	}
	
	/**
	 * 我的投诉
	 * @return
	 */
	@RequestMapping("/complaint")
	public String complaint(){
		//TODO . 我的投诉页面
		return "trade/complaint";
	}
	
	/**
	 * 统一订单信息跳转接口
	 * @param bn 业务信息  业务类型-业务键
	 * @return
	 */
	@RequestMapping("/order/info/{bn}")
	public String tradeOrder(@PathVariable(value="bn") String bn){
		if(StringUtils.isBlank(bn)){
			throw BisException.me().setCode(ICodes.ILLEGAL_ACCESS);
		}
		String[] bnArr = bn.split("-");
		byte bisType = Byte.parseByte(bnArr[0]);
		Long bisId = Long.parseLong(bnArr[1]);
		
		String url = "redirect:/";
		switch (bisType) {
			case PayBillBusinessTypeConsts.ORDER_PRODUCT:
				url = "redirect:/trade/order/info/product/"+bisId;
				break;
			case PayBillBusinessTypeConsts.ORDER_RECHARGE:
				//TODO .set url
				break;
			case PayBillBusinessTypeConsts.ORDER_REFUND:
				//TODO .set url
				break;
	
			default:
				break;
		}
		
		return url;
	}
	
	/**
	 * 商品订单详细信息页面
	 * @param id 订单id
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/info/product/{id}")
	public String productOrderDetail(@PathVariable(value="id") Long id,Model model){
		Order order = userCenterService.orderGet(id);
		Map<String, Object> extMap = new HashMap<>();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		extMap.put("createTime", format.format(new Date(order.getCreateTime())));
		if(order.getState()==OrderStateConsts.WAIT_PAY){
			extMap.put("lastPayTime", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(order.getLastPayTime())));
		}
		if(order.getState()==OrderStateConsts.WAIT_SHIP_TAKE){
			extMap.put("lastConfirmTime", new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(order.getLastConfirmShipTime())));
		}
		if(order.getPayTime()!=null){
			extMap.put("payTime", format.format(new Date(order.getPayTime())));
		}
		if(order.getShipSendTime()!=null){
			extMap.put("shipSendTime", format.format(new Date(order.getShipSendTime())));
		}
		if(order.getFinishedTime()!=null){
			extMap.put("finishedTime", format.format(new Date(order.getFinishedTime())));
		}
		if(order.getCommentTime()!=null){
			extMap.put("commentTime", format.format(new Date(order.getCommentTime())));
		}
		
		model.addAttribute("order", order);
		model.addAttribute("extMap", extMap);
		return "trade/order.detail";
	}
	
	/**
	 * 我的订单
	 * @return
	 */
	@RequestMapping("/order")
	public String order(){
		return "trade/order";
	}
	
	/**
	 * 我的订单 - 订单列表数据
	 * @param model
	 * @param query
	 * @return
	 */
	@RequestMapping("/order/list")
	public String orderList(Model model,OrderQuery query){
		query.setSsoId(SsoContext.getSsoId());
		Integer year = 0;
		if(null!=query){
			year = query.getYear();
		}
		//如果是评论，增加过滤退换货状态订单的条件
		if(query.getIsComment()!=null){
			query.setSaleReturnState(0);
		}
		
		Map<String, Integer> orderStaisMap = userCenterService.orderStatisBySso(SsoContext.getSsoId(),year);
		model.addAttribute("staJson", JSONObject.toJSONString(orderStaisMap));
		
		Page<Order> orderPage = userCenterService.orderQuery(query);
		List<Order> rows = orderPage.getRows();
		Map<String, Object> extMap = new HashMap<>();
		for (Order order : rows) {
			Byte state = order.getState();
			String stateName = ConstUtils.getBisConstName(OrderStateConsts.class, state.intValue());
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			extMap.put("date"+order.getId(), dateFormat.format(new Date(order.getCreateTime())));
			extMap.put("stateName"+order.getId(), stateName);
			if(order.getState() == OrderStateConsts.WAIT_PAY){
				dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				long lastPayTime = order.getLastPayTime();
				// 05/15/2017 16:40:24
				Date date = new Date(lastPayTime);
				String dateFormatText = dateFormat.format(date);
				extMap.put("payDate"+order.getId(), dateFormatText);
			}
			if(order.getState() == OrderStateConsts.WAIT_SHIP_TAKE){
				dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
				long lastConfirmDate = order.getLastConfirmShipTime();
				// 05/15/2017 16:40:24
				Date date = new Date(lastConfirmDate);
				String dateFormatText = dateFormat.format(date);
				extMap.put("lastConfirmDate"+order.getId(), dateFormatText);
			}
			
		}
		model.addAttribute("page", orderPage);
		model.addAttribute("extMap", extMap);
		return "trade/order.list";
	}
	
	/**
	 * 退款/退货
	 * @return
	 */
	@RequestMapping("/refund")
	public String refund(){
		//TODO 退款页面
		return "trade/refund";
	}
	
	/**
	 * 取消订单
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/order/cancel")
	@ResponseBody
	public Ret orderCancel(Long orderId){
		userCenterService.orderCancel(SsoContext.getSsoId(), orderId);
		return Ret.me();
	}
	
	/**
	 * 删除订单
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/order/delete")
	@ResponseBody
	public Ret orderDelete(String orderId){
		if(StringUtils.isBlank(orderId)){
			throw BisException.me().setCode(ICodes.ILLEGAL_ACCESS);
		}
		userCenterService.orderDelete(SsoContext.getSsoId(),StrUtils.splitToLong(orderId));
		return Ret.me();
	}
	
	/**
	 * 确认收货
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/order/confirmShip")
	@ResponseBody
	public Ret confirmShip(Long orderId){
		userCenterService.orderConfirmFinish(SsoContext.getSsoId(), orderId);
		return Ret.me();
	}
}
