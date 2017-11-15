package cn.itsource.aigou.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itsource.aigou.core.consts.ICodes;
import cn.itsource.aigou.core.consts.msg.UserCenterMsgConsts;
import cn.itsource.aigou.core.utils.Ret;
import cn.itsource.aigou.facade.CommonService;
import cn.itsource.aigou.facade.UserCenterService;
import cn.itsource.aigou.web.utils.ValidateCode;

@Controller
@RequestMapping("/regist")
public class RegistController {
	
	@Reference
	private UserCenterService userCenterService;
	
	@Reference
	private CommonService commonService;
	
	/**
	 * 验证手机号码是否可注册
	 * @param phone
	 * @return
	 */
	@RequestMapping("/validatePhone")
	@ResponseBody
	public Ret validatePhone(String phone){
		//检测手机号码是否可用
		if(userCenterService.getSsoByPhone(phone)==null){
			return Ret.me();
		}else{
			return Ret.me().setCode(UserCenterMsgConsts.PHONE_NUMBER_EXISTS);
		}
	}
	
	/**
	 * 发送验证码到手机
	 * @param request
	 * @param phone
	 * @param captcha
	 * @return
	 */
	@RequestMapping("/sendSmsCode")
	@ResponseBody
	public Ret sendSmsCode(HttpServletRequest request,String phone,String captcha){
		HttpSession session = request.getSession();  
		String sessionCaptcha = (String) session.getAttribute("captcha");  
		if (!StringUtils.equalsIgnoreCase(captcha, sessionCaptcha)) {  //忽略验证码大小写  
		    return Ret.me().setCode(UserCenterMsgConsts.INVALIDE_CAPTCHA);
		}
		//发送手机验证码并返回结果
		return commonService.sendSmsCode(phone);
	}
	
	/** 
	 * 图形验证码页面 
	 * @return 
	 */  
	@RequestMapping(value="/captcha")  
	public String validateCode(HttpServletRequest request,HttpServletResponse response) throws Exception{  
	    // 设置响应的类型格式为图片格式  
	    response.setContentType("image/jpeg");  
	    //禁止图像缓存。  
	    response.setHeader("Pragma", "no-cache");  
	    response.setHeader("Cache-Control", "no-cache");  
	    response.setDateHeader("Expires", 0);  
	  
	    HttpSession session = request.getSession();  
	  
	    ValidateCode vCode = new ValidateCode(130,40,5,100);  
	    session.setAttribute("captcha", vCode.getCode());  
	    vCode.write(response.getOutputStream());  
	    return null;  
	}
	
	/**
	 * 用户注册接口
	 * @param phone
	 * @param password
	 * @param smsCaptcha
	 * @return
	 */
	@RequestMapping("/reg/phone")
	@ResponseBody
	public Ret regUserByPhone(String phone,String password,String smsCaptcha){
		//做参数验证（格式类） ...
		if(StringUtils.isBlank(phone) || phone.length()<11){
			return Ret.me().setSuccess(false).setCode(ICodes.FAILED).setInfo("请输入正确的电话号码");
		}
		
		Ret ret = userCenterService.regUserByPhone(phone,password,smsCaptcha);
		return ret;
	}
}
