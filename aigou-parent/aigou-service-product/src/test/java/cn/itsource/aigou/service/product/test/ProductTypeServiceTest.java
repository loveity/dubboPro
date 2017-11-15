package cn.itsource.aigou.service.product.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itsource.aigou.core.domain.ProductType;
import cn.itsource.aigou.service.IProductTypeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-service-product.xml")
public class ProductTypeServiceTest {
	@Autowired
	private IProductTypeService productTypeService;
	@Test
	public void testGetTree() throws Exception {
		List<ProductType> allTree = productTypeService.getAllTree(0L);
		System.out.println(allTree);
	}
}
