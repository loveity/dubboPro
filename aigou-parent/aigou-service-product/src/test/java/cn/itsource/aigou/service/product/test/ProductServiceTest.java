package cn.itsource.aigou.service.product.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itsource.aigou.core.domain.Product;
import cn.itsource.aigou.service.IProductService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-service-product.xml")
public class ProductServiceTest {
	
	@Autowired
	private IProductService productService;
	
	@Test
	public void testGet() throws Exception {
		Product product = productService.get(1L);
		System.out.println(product);
	}
	
	@Test
	public void testUpdate() throws Exception {
		Product product = productService.get(1L);
		product.setName("iphone7s");
		productService.update(product);
		System.out.println(productService.get(1L));
	}
}
