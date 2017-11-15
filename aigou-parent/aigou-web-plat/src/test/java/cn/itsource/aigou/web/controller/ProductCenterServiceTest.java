package cn.itsource.aigou.web.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itsource.aigou.core.domain.Employee;
import cn.itsource.aigou.core.domain.PayBill;
import cn.itsource.aigou.core.domain.Product;
import cn.itsource.aigou.core.domain.Sso;
import cn.itsource.aigou.facade.CommonService;
import cn.itsource.aigou.facade.PayCenterService;
import cn.itsource.aigou.facade.PlatManageService;
import cn.itsource.aigou.facade.ProductCenterService;
import cn.itsource.aigou.facade.UserCenterService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring-mvc-plat.xml")
public class ProductCenterServiceTest {
	
	@Reference
	private ProductCenterService productCenterService;
	
	/*@Reference
	private CommonService commonService;
	
	@Reference
	private PayCenterService payCenterService;
	
	@Reference
	private PlatManageService platManageService;
	
	@Reference
	private UserCenterService userCenterService;*/
	
	@Test
	public void testName() throws Exception {
		Product product = productCenterService.getProduct(1L);
		System.out.println("product="+product);
		
		/*boolean isSend = commonService.SendSMS("", "");
		System.out.println("isSend="+isSend);
		
		PayBill payBill = payCenterService.getPayBill(1L);
		System.out.println("payBill="+payBill);
		
		Employee employee = platManageService.getEmployee(1L);
		System.out.println("employee="+employee);
		
		Sso ssoUser = userCenterService.getSsoUser(1L);
		System.out.println("ssoUser="+ssoUser);*/
	}
}
