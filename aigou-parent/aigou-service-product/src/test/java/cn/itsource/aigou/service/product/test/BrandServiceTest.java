package cn.itsource.aigou.service.product.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itsource.aigou.core.domain.Brand;
import cn.itsource.aigou.service.IBrandService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-service-product.xml")
public class BrandServiceTest {
	
	@Autowired
	private IBrandService brandService;
	
	@Test
	public void testGet() throws Exception {
		Brand Brand = brandService.get(1L);
		System.out.println(Brand);
	}
	
	@Test
	public void testUpdate() throws Exception {
		Brand brand = brandService.get(2L);
		brand.setName("阿依莲123");
		brandService.update(brand);
		System.out.println(brandService.get(2L));
	}
	
	@Test
	public void testSave() throws Exception {
		Brand brand = new Brand();
		brand.setName("鄂尔多斯");
		brand.setFirstLetter("B");
		brandService.save(brand);
	}
	
	@Test
	public void testDelete() throws Exception {
		brandService.delete(2L);
		
	}
}
