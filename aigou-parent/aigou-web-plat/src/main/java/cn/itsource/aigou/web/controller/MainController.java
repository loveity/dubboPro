package cn.itsource.aigou.web.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itsource.aigou.core.consts.ConstUtils;
import cn.itsource.aigou.core.utils.KV;
import cn.itsource.aigou.core.utils.Ret;

@Controller
public class MainController {
	/**
	 * 后台主页视图
	 * @return
	 */
	@RequestMapping("/main")
	public String index(){
		return "common/index";
	}
	/**
	 * 个人工作台视图
	 * @return
	 */
	@RequestMapping("/main/dashbord")
	public String dashbord(){
		return "common/dashbord";
	}
	/**
	 * 锁定登录视图
	 * @return
	 */
	/*@RequestMapping("/login/lock")
	public String lock(){
		return "common/lock";
	}*/
	
	/**
	 * 注销当前登录用户
	 * @return
	 */
	@RequestMapping("/login/logout")
	public String logout(){
		return "common/login";
	}
	/**
	 * 后台登录页视图
	 * @return
	 */
	@RequestMapping("/login")
	public String login(){
		return "common/login";
	}
	/**
	 * 后台用户登录验证接口
	 * @return
	 */
	@RequestMapping("/login/in")
	@ResponseBody
	public Ret checkLogin(){
		return Ret.me();
	}
	
	/**
	 * 获取后台常量KV列表
	 * 如：请求属性类型KV列表
	 * 前台请求地址： /const/propertyType
	 * @param constName
	 * @return
	 */
	@RequestMapping("/const/{constName}")
	@ResponseBody
	public List<KV<Integer,String>> getConstList(@PathVariable(value="constName") String constName){
		Class<?> clazz;
		try {
			String constPrefix = constName.substring(0, 1).toUpperCase() + constName.substring(1);
			String className = "cn.itsource.aigou.core.consts.bis."+constPrefix+"Consts";
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return Collections.EMPTY_LIST;
		}
		List<KV<Integer, String>> kvList = ConstUtils.getKvList(clazz);
		return kvList;
	}
}
