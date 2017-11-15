package cn.itsource.aigou.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 会员中心
 * @author nixianhua
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@RequestMapping("/address")
	public String address(){
		
		return "user/address";
	}
	
	@RequestMapping("/growth")
	public String growth(){
		
		return "user/growth";
	}
	
	@RequestMapping("/message")
	public String message(){
		
		return "user/message";
	}
	
	@RequestMapping("/profile")
	public String profile(){
		
		return "user/profile";
	}
	
	@RequestMapping("/secret")
	public String secret(){
		
		return "user/secret";
	}
}
