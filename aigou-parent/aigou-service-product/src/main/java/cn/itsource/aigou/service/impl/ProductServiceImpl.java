package cn.itsource.aigou.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.consts.bis.ProductStateConsts;
import cn.itsource.aigou.core.domain.Brand;
import cn.itsource.aigou.core.domain.Product;
import cn.itsource.aigou.core.domain.ProductExt;
import cn.itsource.aigou.core.domain.ProductMedia;
import cn.itsource.aigou.core.domain.ProductType;
import cn.itsource.aigou.core.domain.ProductViewProperty;
import cn.itsource.aigou.core.domain.Property;
import cn.itsource.aigou.core.domain.Sku;
import cn.itsource.aigou.core.mapper.ProductMapper;
import cn.itsource.aigou.core.utils.CodeGenerateUtils;
import cn.itsource.aigou.facade.CommonService;
import cn.itsource.aigou.service.IBrandService;
import cn.itsource.aigou.service.IProductExtService;
import cn.itsource.aigou.service.IProductMediaService;
import cn.itsource.aigou.service.IProductService;
import cn.itsource.aigou.service.IProductTypeService;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product> implements IProductService {
	@Autowired
	private ProductMapper mapper;

	@Autowired
	private IProductExtService productExtService;

	@Autowired
	private IBrandService brandService;

	@Autowired
	private IProductTypeService productTypeService;

	@Reference
	private CommonService commonService;

	@Override
	protected BaseMapper<Product> getMapper() {
		return mapper;
	}

	/**
	 * 公共的保存商品信息初始化方法
	 * 
	 * @param product
	 */
	private void initProduct(Product product) {
		String code = CodeGenerateUtils.generateProductCode();
		product.setCode(code);
		product.setState(ProductStateConsts.PRODUCT_STATE_OFFSALE);
		product.setMaxPrice(1);
		product.setMinPrice(1);
		product.setGoodCommentCount(0);
		product.setCommonCommentCount(0);
		product.setGoodCommentCount(0);
		product.setBadCommentCount(0);
		product.setCreateTime(System.currentTimeMillis());
		product.setUpdateTime(System.currentTimeMillis());
	}

	@Override
	public void savePart(Product product) {
		this.initProduct(product);
		super.savePart(product);
		// 保存扩展信息
		this.storeExtInfo(product);
		// 保存商品图片
		this.storeMedia(product);
	}

	@Override
	public void updatePart(Product product) {
		super.updatePart(product);
		// 保存扩展信息
		this.storeExtInfo(product);
		// 保存商品图片
		this.storeMedia(product);
		// 提交更新到es
		Map<String, Object> productMap = this.buildEsMapData(product.getId());
		if(Byte.valueOf(productMap.get("state").toString())==ProductStateConsts.PRODUCT_STATE_ONSALE){
			commonService.saveOrUpdateProduct2Es(productMap.get("id").toString(), JSONObject.toJSONString(productMap));
		}
	}

	/**
	 * 存储商品的扩展信息
	 * 
	 * @param product
	 */
	private void storeExtInfo(Product product) {
		ProductExt productExt = product.getProductExt();
		productExt.setProductId(product.getId());
		ProductExt existExt = productExtService.getByProductId(product.getId());
		if (existExt != null) {
			productExt.setId(existExt.getId());
			productExtService.updatePart(productExt);
		} else {
			productExtService.savePart(productExt);
		}
	}

	/**
	 * 保存商品图片
	 * 
	 * @param product
	 */
	private void storeMedia(Product product) {
		mapper.deleteMedias(product.getId());
		List<ProductMedia> productMediaList = product.getProductMediaList();
		if (null != productMediaList) {
			for (ProductMedia productMedia : productMediaList) {
				productMedia.setProductId(product.getId());
			}
			if (productMediaList != null && productMediaList.size() > 0) {
				mapper.saveMedias(productMediaList);
			}
		}
	}

	@Override
	public List<Property> getProperties(Long productId) {
		return mapper.getProperties(productId);
	}

	@Override
	public List<Property> getViewProperties(Long productId) {
		return mapper.getViewProperties(productId);
	}

	@Override
	public List<Property> getSkuProperties(Long productId) {
		return mapper.getSkuProperties(productId);
	}

	@Override
	public void storeViewProperties(Long productId, List<ProductViewProperty> productViewPropertyList) {
		mapper.deleteViewProperties(productId);
		if (productViewPropertyList != null && productViewPropertyList.size() > 0) {
			mapper.saveViewProperties(productViewPropertyList);

			List<String> viewPropertyList = new ArrayList<>();
			for (ProductViewProperty productViewProperty : productViewPropertyList) {
				String propName = productViewProperty.getPropName();
				if (StringUtils.isBlank(propName)) {
					propName = "";
				} else {
					propName = propName.replace(":", "：").replace("_", "-");
				}
				String propValue = productViewProperty.getValue();
				if (StringUtils.isBlank(propValue)) {
					propValue = "";
				} else {
					propValue = propValue.replace(":", "：").replace("_", "-");
				}

				// propId:propName:optionId:optionName(value)
				String propertyStr = productViewProperty.getPropId() + ":" + propName + ":"
						+ productViewProperty.getOptionId() + ":" + propValue;
				viewPropertyList.add(propertyStr);
			}

			if (viewPropertyList.size() > 0) {
				// propId:propName:optionId:optionName(value)_propId:propName:optionId:optionName(value)
				String viewProperties = StringUtils.join(viewPropertyList, "_");
				// 更新商品的显示属性
				mapper.updateProductViewProperties(productId, viewProperties);

				// 更新到es
				Product product = this.get(productId);
				if(product.getState()==ProductStateConsts.PRODUCT_STATE_ONSALE){
					commonService.saveOrUpdateProduct2Es(productId.toString(),
							JSONObject.toJSONString(product));
				}
			}
		}
	}

	@Override
	public List<ProductViewProperty> getSelectedViewProperties(Long id) {
		return mapper.getSelectedViewProperties(id);
	}

	@Override
	public List<Sku> getSkuList(Long productId) {
		return mapper.getSkuList(productId);
	}

	@Override
	public List<ProductMedia> getMedias(Long id) {
		return mapper.getMedias(id);
	}

	@Override
	public ProductExt getProductExt(Long id) {
		return mapper.getExt(id);
	}

	@Override
	public void onSale(Long[] idArr, Integer onsale) {
		if (idArr != null && idArr.length > 0) {
			
			Map<String, Object> params = new HashMap<>();
			params.put("timestap", System.currentTimeMillis());
			params.put("idArr", idArr);
			if (0 == onsale) {
				mapper.offSale(params);
				// 从es中批量删除
				commonService.deleteEsProducts(idArr);
			} else {
				mapper.onSale(params);
				// 添加到es中
				List<Map<String, Object>> dataList = buildEsMapDataList(idArr);
				commonService.saveOrUpdateProduct2Es(dataList);
			}
		}
	}

	/**
	 * 构造指定商品集合ES Map格式数据的列表
	 * 
	 * @param idArr
	 * @return
	 */
	public List<Map<String, Object>> buildEsMapDataList(Long[] idArr) {
		List<Map<String, Object>> dataList = new ArrayList<>();
		for (Long id : idArr) {
			Map<String, Object> mapData = buildEsMapData(id);
			dataList.add(mapData);
		}
		return dataList;
	}

	/**
	 * 构造指定商品对应的ES MAP格式的数据
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> buildEsMapData(Long id) {
		Map<String, Object> dataMap = new HashMap<>();
		Product product = this.get(id);
		dataMap.put("id", product.getId());
		dataMap.put("name", product.getName());
		dataMap.put("subName", product.getSubName());
		dataMap.put("code", product.getCode());
		
		//获取商品所有分类ID
		Long productTypeId = product.getProductType();
		ProductType productType = productTypeService.get(productTypeId);
		if(null!=productType){
			String path = productType.getPath();
			path = path.substring(1, path.length()-1);
			String[] typeIdsArr = path.split("\\.");
			Long[] productTypeArr = new Long[typeIdsArr.length];
			for (int i=0;i<typeIdsArr.length;i++) {
				productTypeArr[i] = Long.parseLong(typeIdsArr[i]);
			}
			dataMap.put("productType", productTypeArr);
		}
		dataMap.put("onSaleTime", product.getOnSaleTime());
		dataMap.put("offSaleTime", product.getOffSaleTime());
		dataMap.put("brandId", product.getBrandId());
		dataMap.put("state", product.getState());
		dataMap.put("maxPrice", product.getMaxPrice());
		dataMap.put("minPrice", product.getMinPrice());
		dataMap.put("viewProperties", product.getViewProperties());
		dataMap.put("commentCount", product.getCommentCount());
		dataMap.put("saleCount", product.getSaleCount());
		dataMap.put("viewCount", product.getViewCount());

		Brand brand = brandService.get(product.getBrandId());
		if (brand != null) {
			dataMap.put("brandName", brand.getName());
		}

		if (null != productType) {
			dataMap.put("productTypeName", productType.getName());
		}

		List<ProductMedia> medias = this.getMedias(id);
		if (null != medias && medias.size() > 0) {
			String[] images = new String[medias.size()];
			for (int i = 0; i < medias.size(); i++) {
				images[i] = medias.get(i).getResource();
			}
			dataMap.put("images", images);
		}

		ProductExt productExt = this.getProductExt(id);
		if (null != productExt) {
			dataMap.put("description", productExt.getDescription());
		}

		return dataMap;
	}

}
