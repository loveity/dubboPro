package cn.itsource.aigou.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 资金中心
 * @author nixianhua
 *
 */
@Controller
@RequestMapping("/account")
public class AccountController {
	@RequestMapping("/home")
	public String home(){
		
		return "account/home";
	}
	
	@RequestMapping("/points")
	public String points(){
		
		return "account/points";
	}
	
	@RequestMapping("/recharge")
	public String recharge(){
		
		return "account/recharge";
	}
	
	@RequestMapping("/coupon")
	public String coupon(){
		
		return "account/coupon";
	}
}
