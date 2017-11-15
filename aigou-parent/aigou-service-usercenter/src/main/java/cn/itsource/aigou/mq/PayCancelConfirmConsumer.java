package cn.itsource.aigou.mq;

import java.util.Map;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;

import cn.itsource.aigou.core.consts.bis.PayBillBusinessTypeConsts;
import cn.itsource.aigou.service.IOrderService;
/**
 *（个人中心）接收支付取消成功关闭订单交易 -> 确认订单已取消 
 * @author nixianhua
 *
 */
public class PayCancelConfirmConsumer {
	
	@Autowired
	private IOrderService orderService;
	
	public void receiveMessage(Map message) throws JMSException{
		System.out.println("收到消息："+message);
		boolean success = Boolean.valueOf(message.get("success").toString());
		byte oldBisType = Byte.valueOf(message.get("oldBisType").toString());
		long oldBisKey = Long.parseLong(message.get("oldBisKey").toString());
		byte newBisType = Byte.valueOf(message.get("newBisType").toString());
		System.out.println("处理订单："+message);
		
		switch (newBisType) {
			case PayBillBusinessTypeConsts.ORDER_CANCEL:
				dealOrderCancelConfirm(success,oldBisKey,message);
				break;
	
			case PayBillBusinessTypeConsts.ORDER_REFUND:
				//TODO .处理退换货 支付退款确认业务
				break;
				
			default:
				break;
		}
		
	}
	
	/**
	 * 订单取消 支付退款 确认业务
	 * @param success 
	 * @param oldBisKey 原业务id  订单id
	 * @param message 消息
	 */
	private void dealOrderCancelConfirm(Boolean success,Long oldBisKey,Map message){
		if(success){//支付取消/退款处理成功
			orderService.cancelFinished(oldBisKey);
		}else{
			orderService.cancelFailed(oldBisKey);
		}
	}
}
