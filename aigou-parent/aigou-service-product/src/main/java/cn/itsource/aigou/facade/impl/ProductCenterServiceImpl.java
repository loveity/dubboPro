package cn.itsource.aigou.facade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;

import cn.itsource.aigou.core.domain.Brand;
import cn.itsource.aigou.core.domain.Product;
import cn.itsource.aigou.core.domain.ProductExt;
import cn.itsource.aigou.core.domain.ProductMedia;
import cn.itsource.aigou.core.domain.ProductType;
import cn.itsource.aigou.core.domain.ProductViewProperty;
import cn.itsource.aigou.core.domain.Property;
import cn.itsource.aigou.core.domain.PropertyOption;
import cn.itsource.aigou.core.domain.Sku;
import cn.itsource.aigou.core.query.BaseQuery;
import cn.itsource.aigou.core.utils.LetterUtil;
import cn.itsource.aigou.core.utils.Page;
import cn.itsource.aigou.facade.CommonService;
import cn.itsource.aigou.facade.ProductCenterService;
import cn.itsource.aigou.service.IBrandService;
import cn.itsource.aigou.service.IProductService;
import cn.itsource.aigou.service.IProductTypeService;
import cn.itsource.aigou.service.IPropertyOptionService;
import cn.itsource.aigou.service.IPropertyService;
import cn.itsource.aigou.service.ISkuService;

@Service
public class ProductCenterServiceImpl implements ProductCenterService{

	@Autowired
	private IProductService productService;
	@Autowired
	private IProductTypeService productTypeService;
	@Autowired
	private IPropertyService propertyService;
	@Autowired
	private IPropertyOptionService propertyOptionService;
	@Autowired
	private IBrandService brandService;
	@Autowired
	private ISkuService skuService;
	@Reference
	private CommonService commonService;
	
	public Product getProduct(Long id) {
		return productService.get(id);
	}
	
	@Override
	public Page<Product> getProductPage(BaseQuery qObject) {
		return productService.queryPage(qObject);
	}
	
	@Override
	public Product saveOrUpdateProduct(Product product) {
		if(product.getId()==null){
			productService.savePart(product);
		}else{
			productService.updatePart(product);
		}
		return product;
	}

	@Override
	public void deleteProduct(String ids) {
		String[] idArr = ids.split(",");
		Long[] idLongs = new Long[idArr.length];
		for (int i=0;i<idArr.length;i++) {
			Long id = Long.valueOf(idArr[i]);
			productService.delete(id);
			idLongs[i] = id;
		}
		//从ES中删除商品
		commonService.deleteEsProducts(idLongs);
	}

	@Override
	public ProductType getProductType(Long id) {
		return productTypeService.get(id);
	}

	@Override
	public ProductType saveOrUpdateProductType(ProductType productType) {
		if(null!=productType){
			productTypeService.updatePart(productType);
		}else{
			productTypeService.savePart(productType);
		}
		return productType;
	}

	@Override
	public List<ProductType> getAllProductType() {
		return productTypeService.getAll();
	}

	@Override
	public void deleteProductType(String ids) {
		String[] idArr = ids.split(",");
		for (String idStr : idArr) {
			Long id = Long.valueOf(idStr);
			productTypeService.delete(id);
		}
	}

	@Override
	public List<ProductType> getAllProductTypeTree() {
		return productTypeService.getAllTree(0L);
	}
	
	@Override
	public List<Map<String, Object>> getTypeBreadcrumb(Long productTypeId) {
		return productTypeService.getTypeBreadcrumb(productTypeId);
	}

	@Override
	public Property getProperty(Long id) {
		return propertyService.get(id);
	}

	@Override
	public void deleteProperty(String ids) {
		String[] idArr = ids.split(",");
		for (String idStr : idArr) {
			Long id = Long.valueOf(idStr);
			propertyService.delete(id);
		}
	}

	@Override
	public Property saveOrUpdateProperty(Property property) {
		if(property.getId()!=null){
			propertyService.updatePart(property);
		}else{
			propertyService.savePart(property);
		}
		return property;
	}

	@Override
	public List<Property> getProperties(BaseQuery qObject) {
		return propertyService.getProperties(qObject);
	}

	@Override
	public PropertyOption getPropertyOption(Long id) {
		return propertyOptionService.get(id);
	}

	@Override
	public void deletePropertyOption(String ids) {
		String[] idArr = ids.split(",");
		for (String idStr : idArr) {
			Long id = Long.valueOf(idStr);
			propertyOptionService.delete(id);
		}
	}

	@Override
	public PropertyOption saveOrUpdatePropertyOption(PropertyOption propertyOption) {
		if(propertyOption.getId()!=null){
			propertyOptionService.updatePart(propertyOption);
		}else{
			propertyOptionService.savePart(propertyOption);
		}
		return propertyOption;
	}

	@Override
	public List<PropertyOption> getPropertieOptions(BaseQuery qObject) {
		return propertyOptionService.getPropertieOptions(qObject);
	}

	@Override
	public Page<Brand> getBrandPage(BaseQuery qObject) {
		return brandService.queryPage(qObject);
	}

	@Override
	public Brand getBrand(Long id) {
		return brandService.get(id);
	}
	

	@Override
	public List<Brand> getBrandsByProductType(Long productType) {
		return brandService.getBrandsByProductType(productType);
	}

	@Override
	public Brand saveOrUpdateBrand(Brand brand) {
		if(null!=brand.getId()){
			brandService.updatePart(brand);
		}else{
			brandService.savePart(brand);
		}
		return brand;
	}

	@Override
	public void deleteBrand(String ids) {
		String[] idArr = ids.split(",");
		for (String idStr : idArr) {
			Long id = Long.valueOf(idStr);
			brandService.delete(id);
		}		
	}

	@Override
	public List<Property> getProductProperties(Long id) {
		return productService.getProperties(id);
	}
	
	@Override
	public List<Property> getViewProperties(Long id) {
		return productService.getViewProperties(id);
	}

	@Override
	public List<Property> getSkuProperties(Long id) {
		return productService.getSkuProperties(id);
	}

	@Override
	public List<PropertyOption> getPropertyOptions(Long id) {
		return propertyService.getOptions(id);
	}

	@Override
	public void saveProductViewProperties(Long productId,List<ProductViewProperty> productViewPropertyList) {
		productService.storeViewProperties(productId,productViewPropertyList);
	}

	@Override
	public List<ProductViewProperty> getProductViewProperties(Long id) {
		return productService.getSelectedViewProperties(id);
	}

	@Override
	public List<Sku> getSkuList(Long productId) {
		return productService.getSkuList(productId);
	}

	@Override
	public void saveSku(Sku sku) {
		skuService.saveSku(sku);
	}

	@Override
	public void updateSku(Sku sku) {
		skuService.updatePart(sku);
	}
	
	@Override
	public Sku getSku(Long id) {
		return skuService.get(id);
	}

	@Override
	public List<ProductMedia> getProductMedias(Long id) {
		return productService.getMedias(id);
	}

	@Override
	public ProductExt getProductExt(Long id) {
		return productService.getProductExt(id);
	}

	@Override
	public void onSale(Long[] idArr, Integer onsale) {
		productService.onSale(idArr,onsale);
	}

	@Override
	public void recoverSkuStock(Long skuId, Integer amount) {
		skuService.recoverStock(skuId,amount);
	}

	@Override
	public void outboudSku(Long skuId, Integer amount) {
		skuService.outbound(skuId,amount);
	}

	@Override
	public Sku getSkuByCode(String skuCode) {
		return skuService.getByCode(skuCode);
	}



}
