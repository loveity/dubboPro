package cn.itsource.aigou.mq;

import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;

import cn.itsource.aigou.core.consts.bis.PayBillBusinessTypeConsts;
import cn.itsource.aigou.core.consts.bis.PayStateConsts;
import cn.itsource.aigou.core.domain.PayBill;
import cn.itsource.aigou.core.utils.CodeGenerateUtils;
import cn.itsource.aigou.pay.utils.AlipayUtils;
import cn.itsource.aigou.service.IPayBillService;

public class PayCancelApllyConsumer {
	@Autowired
	private JmsTemplate jmsTemplate ;
	
	@Autowired
	@Qualifier("payCancelConfirmQueueDestination")
	private Destination payCancelConfirmQueueDestination;
	
	@Autowired
	private IPayBillService payBillService;
	
	public void receiveMessage(Map message) throws JMSException{
		System.out.println("收到消息："+message);
		System.out.println("处理消息....");
		//（支付中心）接收支付取消消息，查询支付单 -> 
		byte oldBisType = Byte.valueOf(message.get("oldBisType").toString());
		long oldBisKey = Long.valueOf(message.get("oldBisKey").toString());
		byte newBisType = Byte.valueOf(message.get("newBisType").toString());
		
		PayCancelConfirmMessage confirmMessage = new PayCancelConfirmMessage();
		confirmMessage.setOldBisType(oldBisType);
		confirmMessage.setOldBisKey(oldBisKey);
		confirmMessage.setNewBisType(newBisType);
		
		PayBill payBill = payBillService.getByBusinessKey(oldBisType, oldBisKey);
		
		//未找到支付单
		if(payBill == null){
			System.out.println("payBill 找不到");
			confirmMessage.setSuccess(false);
			confirmMessage.setOldBisKey(oldBisKey);
			confirmMessage.setOldBisType(oldBisType);
			confirmMessage.setNewBisType(newBisType);
			if(message.get("newBisKey")!=null){
				confirmMessage.setNewBisKey(Long.parseLong(message.get("newBisKey").toString()));
			}
		}else{
			switch (newBisType) {
				case PayBillBusinessTypeConsts.ORDER_CANCEL:
					dealCancelOrderBis(message, payBill,confirmMessage);
					break;
				case PayBillBusinessTypeConsts.ORDER_REFUND:
					//TODO . 处理支付退款
					break;
				default:
					break;
			}
		}
		System.out.println("发送确认消息");
		//（支付中心）生成支付取消成功确认消息 -> 队列 -> 
		jmsTemplate.send(payCancelConfirmQueueDestination, confirmMessage);
	}

	/**
	 * 订单取消处理逻辑
	 * @param message
	 * @param payBill
	 * @param confirmMessage
	 */
	private void dealCancelOrderBis(Map message, PayBill payBill,final PayCancelConfirmMessage confirmMessage) {
		//待付款：  （支付中心）关闭三方支付交易、关闭支付单支付  
		if(payBill.getState() == PayStateConsts.WAIT_PAY){
			System.out.println("还没有付款，直接取消");
			//支付宝关闭交易
			boolean cancelSuccess = AlipayUtils.closeTrade(payBill.getUnionPaySn());
			System.out.println("支付宝关闭交易结果："+cancelSuccess);
			//关闭支付单支付
			payBill.setState(PayStateConsts.FAIL);
			payBillService.updatePart(payBill);
		}
		//已付款：（支付中心）创建取消订单支付单、关闭三方支付交易（三方退款）、记录退款流水 -> 发送退款短信通知
		if(payBill.getState() == PayStateConsts.PAYED){
			System.out.println("已经付款，先关闭交易（退款），");
			//支付宝关闭交易
			boolean cancelSuccess = AlipayUtils.refund(payBill.getUnionPaySn(), payBill.getMoney(), null, null);
			System.out.println("支付宝关闭交易结果："+cancelSuccess);
			confirmMessage.setSuccess(cancelSuccess);
			if(cancelSuccess){
				//创建取消订单支付单
				PayBill cancelPayBill = new PayBill();
				cancelPayBill.setBusinessKey(confirmMessage.getOldBisKey());
				cancelPayBill.setBusinessType(PayBillBusinessTypeConsts.ORDER_CANCEL);
				cancelPayBill.setCreateTime(System.currentTimeMillis());
				cancelPayBill.setUpdateTime(cancelPayBill.getCreateTime());
				cancelPayBill.setSsoId(payBill.getSsoId());
				String digest = "订单取消退款"+payBill.getUnionPaySn();
				cancelPayBill.setLastPayTime(System.currentTimeMillis());
				cancelPayBill.setDigest(digest);
				cancelPayBill.setMoney(payBill.getMoney());// 支付金额
				cancelPayBill.setNote(digest);
				cancelPayBill.setPayChannel(payBill.getPayChannel());
				cancelPayBill.setState(PayStateConsts.PAYED);
				String unionPaySn = CodeGenerateUtils.generateUnionPaySn();// 统一支付单号
				cancelPayBill.setUnionPaySn(unionPaySn);
				cancelPayBill.setOriginalPayBillId(payBill.getId());
				cancelPayBill.setOriginalUnionPaySn(payBill.getUnionPaySn());
				payBillService.savePart(cancelPayBill);
				
				//创建退款流水
				payBillService.createPayAccountFlow(cancelPayBill);
				
				//TODO .发送退款短信通知
				System.out.println("您的爱购网退款将于7个工作日内原路返回，请注意查收，退款金额："+(payBill.getMoney()*0.01)+"元");
			}
		}
	}
	
}
