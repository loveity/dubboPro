package cn.itsource.aigou.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.response.AlipayTradeQueryResponse;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.consts.ConstUtils;
import cn.itsource.aigou.core.consts.bis.AccountFlowConsts;
import cn.itsource.aigou.core.consts.bis.PayBillBusinessTypeConsts;
import cn.itsource.aigou.core.consts.bis.PayChannelConsts;
import cn.itsource.aigou.core.consts.bis.PayInteractionStateConsts;
import cn.itsource.aigou.core.consts.bis.PayStateConsts;
import cn.itsource.aigou.core.domain.Order;
import cn.itsource.aigou.core.domain.PayAccount;
import cn.itsource.aigou.core.domain.PayAccountFlow;
import cn.itsource.aigou.core.domain.PayBill;
import cn.itsource.aigou.core.domain.PayInteraction;
import cn.itsource.aigou.core.exception.BisException;
import cn.itsource.aigou.core.mapper.PayBillMapper;
import cn.itsource.aigou.core.utils.CodeGenerateUtils;
import cn.itsource.aigou.pay.utils.AlipayUtils;
import cn.itsource.aigou.service.IPayAccountFlowService;
import cn.itsource.aigou.service.IPayAccountService;
import cn.itsource.aigou.service.IPayBillService;
import cn.itsource.aigou.service.IPayInteractionService;

@Service
public class PayBillServiceImpl extends BaseServiceImpl<PayBill> implements IPayBillService {
	@Autowired
	private PayBillMapper mapper;

	@Autowired
	private IPayInteractionService payInteractionService;
	
	@Autowired
	private IPayAccountFlowService accountFlowService;
	
	@Autowired
	private IPayAccountService payAccountService;

	@Override
	protected BaseMapper<PayBill> getMapper() {
		return mapper;
	}

	@Override
	public PayBill createProductOrderBill(Order order) {
		// 如果已经创建商品订单支付单，不能重复创建
		// 通过业务类型和业务ID查询商品订单支付业务类型的支付单
		PayBill payBill = mapper.queryBill(PayBillBusinessTypeConsts.ORDER_PRODUCT, order.getId());
		if (payBill != null) {
			return payBill;
		}

		// 创建新的支付单
		payBill = new PayBill();

		payBill.setBusinessKey(order.getId());
		payBill.setBusinessType(PayBillBusinessTypeConsts.ORDER_PRODUCT);
		payBill.setCreateTime(System.currentTimeMillis());
		payBill.setUpdateTime(payBill.getCreateTime());
		payBill.setSsoId(order.getSsoId());
		String digest = order.getDigest();
		if (digest.length() > 100) {
			digest = digest.substring(0, 99);
		}
		payBill.setLastPayTime(order.getLastPayTime());
		payBill.setDigest(digest);
		payBill.setMoney(order.getRealMoney());// 支付金额
		payBill.setNote("商品订单支付" + order.getOrderSn());
		payBill.setPayChannel(order.getPayChannel());
		payBill.setState(PayStateConsts.WAIT_PAY);
		String unionPaySn = CodeGenerateUtils.generateUnionPaySn();// 统一支付单号
		payBill.setUnionPaySn(unionPaySn);

		this.savePart(payBill);

		// 非账户余额支付（第三方支付）创建三方接口通知记录
		if (PayChannelConsts.ACCOUNT != payBill.getPayChannel()) {
			createPayInteraction(payBill);
		}

		return payBill;
	}

	/**
	 * 根据支付单数据创建第三方支付接口通知记录
	 * 
	 * @param payBill
	 * @return
	 */
	private PayInteraction createPayInteraction(PayBill payBill) {
		PayInteraction payInteraction = new PayInteraction();
		payInteraction.setCreateTime(System.currentTimeMillis());
		payInteraction.setMoney(payBill.getMoney());
		payInteraction.setPayBillId(payBill.getId());
		payInteraction.setPayChannel(payBill.getPayChannel());
		payInteraction.setState(PayInteractionStateConsts.WAIT);
		payInteraction.setUnionPaySn(payBill.getUnionPaySn());
		payInteraction.setUpdateTime(payInteraction.getCreateTime());

		payInteractionService.savePart(payInteraction);
		return payInteraction;
	}

	@Override
	public PayBill getPayBill(String unionPaySn) {
		return mapper.getPayBill(unionPaySn);
	}

	@Override
	public PayBill notifyBillPayed(String unionPaySn, String totalAmount, String tradeNo,
			Object parameterMap) {
		PayBill payBill = this.getPayBill(unionPaySn);
		if (payBill.getState() != PayStateConsts.WAIT_PAY) { // 已处理过，直接返回
			return payBill;
		}

		// 未处理，正常通知
		BigDecimal totalMoney = new BigDecimal(totalAmount).multiply(new BigDecimal(100));
		// 判断支付金额是否正确
		if (totalMoney.intValue() != payBill.getMoney()) {
			throw BisException.me();
		}

		// 记录三方接口支付记录
		if (payBill.getPayChannel() != PayChannelConsts.ACCOUNT) {
			updatePayInteraction(payBill, tradeNo, parameterMap);
		}
		// 记录支付流水
		createPayAccountFlow(payBill);
		// 修改支付单状态
		payBill.setState(PayStateConsts.PAYED);
		payBill.setUpdateTime(System.currentTimeMillis());
		this.updatePart(payBill);
		
		return payBill;
	}

	/**
	 * 更新记录三方接口通知内容
	 * 
	 * @param payBill
	 *            支付单
	 * @param paramMap
	 *            支付报文
	 * @return
	 */
	private PayInteraction updatePayInteraction(PayBill payBill, String tradeNo, Object paramMap) {
		PayInteraction payInteraction = payInteractionService.getByBillId(payBill.getId());
		if (null == payInteraction) {
			this.createPayInteraction(payBill);
		}
		payInteraction.setNotifyTime(System.currentTimeMillis());
		payInteraction.setReactionData(JSONObject.toJSONString(paramMap));
		payInteraction.setReturnMoney(payBill.getMoney());
		payInteraction.setReturnSeq(tradeNo);
		payInteraction.setState(PayInteractionStateConsts.NOTIFIED);
		payInteraction.setUpdateTime(System.currentTimeMillis());

		payInteractionService.updatePart(payInteraction);

		return payInteraction;
	}

	/**
	 * 通过支付单创建支付流水
	 * @param payBill
	 * @return
	 */
	@Override
	public PayAccountFlow createPayAccountFlow(PayBill payBill) {
		
		PayAccount account = payAccountService.getBySso(payBill.getSsoId());
		
		PayAccountFlow accountFlow = new PayAccountFlow();

		accountFlow.setCreateTime(System.currentTimeMillis());
		accountFlow.setSsoId(payBill.getSsoId());
		accountFlow.setNickName("");
		accountFlow.setMoney(payBill.getMoney());
		if(payBill.getBusinessType()==PayBillBusinessTypeConsts.ORDER_REFUND 
				|| payBill.getBusinessType()==PayBillBusinessTypeConsts.ORDER_CANCEL){
			accountFlow.setType(AccountFlowConsts.INCOME);
		}else{
			accountFlow.setType(AccountFlowConsts.OUTCOME);
		}
		accountFlow.setAvilableBalance(account.getUseableBalance());
		accountFlow.setFrozenBalance(account.getFrozenBalance());
		accountFlow.setBusinessType(payBill.getBusinessType());
		accountFlow.setBusinessKey(payBill.getBusinessKey());
		accountFlow.setBusinessName(ConstUtils.getBisConstName(PayBillBusinessTypeConsts.class, payBill.getBusinessType()));
		accountFlow.setPayChannel(payBill.getPayChannel());
		accountFlow.setPayChannelName(ConstUtils.getBisConstName(PayChannelConsts.class, accountFlow.getPayChannel()));
		accountFlow.setNote(payBill.getNote());
		accountFlow.setDigest(payBill.getDigest());
		accountFlow.setUnionPaySeq(payBill.getUnionPaySn());
		
		accountFlowService.savePart(accountFlow);
		return accountFlow;
	}

	@Override
	public String getUnionPaySnByBusinessKey(int bisType, long bisKey) {
		PayBill payBill = this.getByBusinessKey((byte)bisType, bisKey);
		return null==payBill?"":payBill.getUnionPaySn();
	}

	@Override
	public PayBill dealBillFromAlipay(PayBill payBill) {
		AlipayTradeQueryResponse queryResponse = null;
		try {
			queryResponse = AlipayUtils.queryAlipayTrade(payBill);
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		if (null != queryResponse) { // 查询到支付单已成功支付
			payBill = this.notifyBillPayed(payBill.getUnionPaySn(), queryResponse.getTotalAmount(),
					queryResponse.getTradeNo(), queryResponse.getParams());
		}
		return payBill;
	}

	@Override
	public PayBill getByBusinessKey(byte bisType, long bisKey) {
		return mapper.queryBill((byte)bisType, bisKey);
	}
}
