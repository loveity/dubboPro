package cn.itsource.aigou.facade.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alipay.api.internal.util.AlipaySignature;

import cn.itsource.aigou.core.domain.Order;
import cn.itsource.aigou.core.domain.PayBill;
import cn.itsource.aigou.core.domain.Sso;
import cn.itsource.aigou.facade.PayCenterService;
import cn.itsource.aigou.pay.utils.AlipayUtils;
import cn.itsource.aigou.service.IPayAccountService;
import cn.itsource.aigou.service.IPayBillService;

@Service
public class PayCenterServiceImpl implements PayCenterService {

	@Autowired
	private IPayBillService payBillService;

	@Autowired
	private IPayAccountService payAccountService;

	@Override
	public PayBill getPayBill(Long id) {
		return payBillService.get(id);
	}

	@Override
	public PayBill getPayBillBySn(String paySn) {
		return payBillService.getPayBill(paySn);
	}

	@Override
	public void createVipAccount(Sso sso) {
		payAccountService.create(sso);
	}

	@Override
	public PayBill orderPayBillCreate(Order order) {
		return payBillService.createProductOrderBill(order);
	}

	@Override
	public PayBill notifyBillPayed(String unionPaySn, String totalAmount, String tradeNo, Object parameterMap) {
		return payBillService.notifyBillPayed(unionPaySn, totalAmount, tradeNo, parameterMap);
	}

	@Override
	public String getUnionPaySnByBusinessKey(int bisType, long bisKey) {
		return payBillService.getUnionPaySnByBusinessKey(bisType, bisKey);
	}

	@Override
	public PayBill dealBillFromAlipay(PayBill payBill) {
		return payBillService.dealBillFromAlipay(payBill);
	}

	@Override
	public String generateAlipayPageData(PayBill payBill) {
		return AlipayUtils.generatePayData(payBill);
	}

	@Override
	public boolean checkAlipaySignature(Map<String, String> params, String aliPubKey, String charset, String signType) {
		try {
			return AlipaySignature.rsaCheckV1(params, aliPubKey, charset, signType);
		} catch (Exception e) {e.printStackTrace();}
		return false;
	}

	@Override
	public boolean validateNotifyParams(String appId) {
		return AlipayUtils.validateNotifyParams(appId);
	}

}
