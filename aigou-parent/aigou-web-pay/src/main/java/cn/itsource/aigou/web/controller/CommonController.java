package cn.itsource.aigou.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itsource.aigou.core.consts.GlobalSettingNames;
import cn.itsource.aigou.core.consts.ICodes;
import cn.itsource.aigou.core.consts.bis.PayChannelConsts;
import cn.itsource.aigou.core.consts.bis.PayStateConsts;
import cn.itsource.aigou.core.domain.PayBill;
import cn.itsource.aigou.core.exception.BisException;
import cn.itsource.aigou.core.utils.GlobalSetting;
import cn.itsource.aigou.facade.PayCenterService;
import cn.itsource.aigou.facade.UserCenterService;

@Controller
public class CommonController {

	@Reference
	private PayCenterService payCenterService;

	@Reference
	private UserCenterService userCenterService;

	@RequestMapping("/gateway")
	public String gateway(String sn,String bn, Model model) {
		if (StringUtils.isBlank(sn) && StringUtils.isBlank(bn)) {
			throw BisException.me().setCode(ICodes.ILLEGAL_ACCESS);
		}

		String unionPaySn = sn;
		if(StringUtils.isBlank(unionPaySn)){
			String[] bnArr = bn.split("-");
			unionPaySn = payCenterService.getUnionPaySnByBusinessKey(Integer.parseInt(bnArr[0]),Long.parseLong(bnArr[1]));
		}
		
		if(StringUtils.isBlank(unionPaySn)){
			throw BisException.me().setCode(ICodes.ILLEGAL_ACCESS);
		}
		
		PayBill payBill = payCenterService.getPayBillBySn(unionPaySn);
		if (payBill == null) {
			throw BisException.me().setCode(ICodes.ILLEGAL_ACCESS);
		}

		byte payChannel = payBill.getPayChannel();
		String payChannelView = "";
		switch (payChannel) {
		case PayChannelConsts.ALIPAY:
			payChannelView = "alipay";
			String payInfo = payCenterService.generateAlipayPageData(payBill);
			model.addAttribute("data", payInfo);
			break;
		case PayChannelConsts.ACCOUNT:
			payChannelView = "account";
			// TODO 账户余额支付页面数据
			break;
		case PayChannelConsts.WECHAT:
			payChannelView = "wechat";
			// TODO 微信扫码支付页面数据
			break;
		case PayChannelConsts.UNION:
			payChannelView = "union";
			// TODO 银联支付页面数据
			break;
		default:
			throw BisException.me().setCode(ICodes.ILLEGAL_ACCESS);
		}
		return "gateway/" + payChannelView;
	}

	@RequestMapping("/alipayNotify")
	public String alipayNotify(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}

		String aliPubKey = GlobalSetting.get(GlobalSettingNames.ALIPAY_RSA256_PUBKEY);
		String charset = GlobalSetting.get(GlobalSettingNames.ALIPAY_CHARSET);
		String signType = GlobalSetting.get(GlobalSettingNames.ALIPAY_SIGN_TYPE);
		// 验证签名
		boolean signVerified = payCenterService.checkAlipaySignature(params, aliPubKey, charset, signType); 

		// ——请在这里编写您的程序（以下代码仅作参考）——

		/*
		 * 实际验证过程建议商户务必添加以下校验： 1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		 * 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		 * 3、校验通知中的seller_id（或者seller_email)
		 * 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		 * 4、验证app_id是否为该商户本身。
		 */
		// AppID
		String appId = request.getParameter("app_id");
		if (payCenterService.validateNotifyParams(appId)) {
			if (signVerified) {// 验证成功
				// 商户订单号
				String out_trade_no = request.getParameter("out_trade_no");
				// 支付宝交易号
				String trade_no = request.getParameter("trade_no");
				// 交易状态
				String trade_status = request.getParameter("trade_status");
				// 交易金额
				String total_amount = request.getParameter("total_amount");

				if (trade_status.equals("TRADE_FINISHED")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序
					// 注意：
					// 退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
					out.println("success");
				} else if (trade_status.equals("TRADE_SUCCESS")) {
					// 判断该笔订单是否在商户网站中已经做过处理
					// 如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					// 如果有做过处理，不执行商户的业务程序
					// 注意：
					// 付款完成后，支付宝系统发送该交易状态通知
					try {
						PayBill payBill = payCenterService.notifyBillPayed(out_trade_no, total_amount, trade_no,
								request.getParameterMap());
						if (payBill.getState() == PayStateConsts.PAYED) {// 通知成功
							userCenterService.notifyOrderPayed(payBill);
						}
						out.println("success");
					} catch (Exception e) {
						e.printStackTrace();
						out.println("fail");
					}
				}
			} else {// 验证失败
				out.println("fail");
				// 调试用，写文本函数记录程序运行情况是否正常
				// String sWord = AlipaySignature.getSignCheckContentV1(params);
				// AlipayConfig.logResult(sWord);
			}
		}

		out.println("fail");
		// ——请在这里编写您的程序（以上代码仅作参考）——
		return null;
	}

	@RequestMapping("/payResult")
	public String payResult(String sn, Model model) {
		PayBill payBill = payCenterService.getPayBillBySn(sn);
		if (payBill.getState() == PayStateConsts.WAIT_PAY) {// 如果结果还是待支付，主动向支付宝发起请求验证订单支付状态
			try {
				/*主动查询后再返回payBill*/
				payBill = payCenterService.dealBillFromAlipay(payBill);
				//如果此时payBill的状态变为了已支付，那么通知订单支付成功
				if(payBill.getState() == PayStateConsts.PAYED){
					userCenterService.notifyOrderPayed(payBill);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		model.addAttribute("payBill", payBill);
		return "payResult";
	}

}
