package cn.itsource.aigou.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;

import cn.itsource.aigou.core.domain.Product;
import cn.itsource.aigou.core.domain.ProductExt;
import cn.itsource.aigou.core.domain.ProductMedia;
import cn.itsource.aigou.core.domain.ProductViewProperty;
import cn.itsource.aigou.core.domain.Property;
import cn.itsource.aigou.core.domain.Sku;
import cn.itsource.aigou.core.domain.SkuProperty;
import cn.itsource.aigou.core.utils.Page;
import cn.itsource.aigou.core.utils.Ret;
import cn.itsource.aigou.core.utils.StrUtils;
import cn.itsource.aigou.facade.ProductCenterService;
import cn.itsource.aigou.facade.query.ProductQuery;
import cn.itsource.aigou.web.consts.ControllerConsts;

@Controller
@RequestMapping("/" + ProductController.DOMAIN)
public class ProductController {
	public static final String DOMAIN = "product";

	@Reference
	private ProductCenterService productCenterService;

	@RequestMapping(ControllerConsts.URL_INDEX)
	public String index() {
		return DOMAIN + ControllerConsts.VIEW_INDEX;
	}

	@RequestMapping(ControllerConsts.URL_JSON)
	@ResponseBody
	public Page<Product> json(ProductQuery qObject) {
		Page<Product> pageResult = productCenterService.getProductPage(qObject);
		return pageResult;
	}

	@RequestMapping(ControllerConsts.URL_EDIT)
	public String edit(Long id, Model model) {
		if (null != id) {
			//获取商品类型并返回给视图
			Product product = productCenterService.getProduct(id);
			model.addAttribute("productType", product.getProductType());

			//获取商品的图片KEY字符串集合
			List<ProductMedia> productMedias = productCenterService.getProductMedias(product.getId());
			StringBuilder resources = new StringBuilder("");
			if(productMedias!=null){
				for (ProductMedia productMedia : productMedias) {
					String resource = productMedia.getResource();
					resources.append(",").append(resource);
				}
			}
			if(resources.length()>0){
				resources.deleteCharAt(0);
			}
			model.addAttribute("resources", resources.toString());
			//获取商品的扩展信息
			ProductExt productExt = productCenterService.getProductExt(product.getId());
			model.addAttribute("productExt", productExt);
			
			model.addAttribute("product", product);
		}
		model.addAttribute("mills", System.currentTimeMillis());
		return DOMAIN + ControllerConsts.VIEW_EDIT;
	}

	@RequestMapping(ControllerConsts.URL_DETAIL)
	@ResponseBody
	public Object detail(Long id) {
		Product o = productCenterService.getProduct(id);
		return o;
	}

	/**
	 * 保存或修改商品信息
	 * @param product 商品信息
	 * @param resources 商品的图片集合
	 * @return
	 */
	@RequestMapping(ControllerConsts.URL_STORE)
	@ResponseBody
	public Ret store(Product product, String resources) {
		if(StringUtils.isNotBlank(resources)){
			List<ProductMedia> medias = new ArrayList<>();
			String[] resourceArr = resources.split(",");
			for (String resource : resourceArr) {
				ProductMedia productMedia = new ProductMedia();
				productMedia.setCreateTime(System.currentTimeMillis());
				productMedia.setUpdateTime(System.currentTimeMillis());
				productMedia.setResource(resource);
				medias.add(productMedia);
			}
			product.setProductMediaList(medias);
		}
		productCenterService.saveOrUpdateProduct(product);
		return Ret.me();
	}

	@RequestMapping(ControllerConsts.URL_DELETE)
	@ResponseBody
	public Ret delete(String id) {
		if (StringUtils.isBlank(id)) {
			return Ret.me().setSuccess(false).setInfo("无效的ID");
		}
		productCenterService.deleteProduct(id);
		return Ret.me().setSuccess(true);
	}

	@RequestMapping(ControllerConsts.URL_SHOW)
	public String show(Long id, Model model) {
		Product o = productCenterService.getProduct(id);
		model.addAttribute("o", o);
		return DOMAIN + ControllerConsts.VIEW_SHOW;
	}

	/**
	 * 商品显示属性表单
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/showPropertyForm")
	public String showPropertyForm(Long id, Model model) {
		Product o = productCenterService.getProduct(id);
		model.addAttribute("product", o);

		// 获取商品已设置的显示属性集及选项值
		List<ProductViewProperty> productViewPropertyList = productCenterService.getProductViewProperties(id);
		model.addAttribute("viewPropertiesList", productViewPropertyList);
		return DOMAIN + "/showPropertyForm";
	}

	/**
	 * 存储商品显示属性列表
	 * @param productId
	 * @param propName
	 * @param value
	 * @return
	 */
	@RequestMapping("/storeShowProperty")
	@ResponseBody
	public Ret storeShowProperty(Long productId, String[] propName, String[] value) {
		List<ProductViewProperty> productViewPropertyList = new ArrayList<>();
		if(null==propName||propName.length==0){
			return Ret.me();
		}
		for (int i = 0; i < propName.length; i++) {
			ProductViewProperty productViewProperty = new ProductViewProperty();
			productViewProperty.setProductId(productId);
			productViewProperty.setPropId(0L);
			productViewProperty.setPropName(propName[i]);
			productViewProperty.setOptionId(0L);
			productViewProperty.setValue(value[i]);
			productViewPropertyList.add(productViewProperty);
		}
		productCenterService.saveProductViewProperties(productId, productViewPropertyList);
		return Ret.me();
	}

	/**
	 * 显示商品的SKU管理页
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/skus")
	public String skus(Long id, Model model) {
		Product o = productCenterService.getProduct(id);
		model.addAttribute("product", o);

		// 获取商品对应的所有sku
		List<Sku> skuList = productCenterService.getSkuList(id);
		model.addAttribute("skuList", skuList);

		return DOMAIN + "/skus";
	}
	
	/**
	 * 返回指定ID的SKU信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/sku/{id}")
	@ResponseBody
	public Sku getSkuJson(@PathVariable(value="id")Long id){
		Sku sku = productCenterService.getSku(id);
		return sku;
	}
	
	/**
	 * 存储商品的SKU列表
	 * @param sku
	 * @param propId
	 * @param propName
	 * @param optionId
	 * @param optionValue
	 * @return
	 */
	@RequestMapping("/storeSku")
	@ResponseBody
	public Ret sotoreSku(Sku sku){
		sku.setMarketPrice(sku.getMarketPrice() * 100);
		sku.setPrice(sku.getPrice() * 100);
		sku.setCostPrice(sku.getCostPrice() * 100);
		productCenterService.saveSku(sku);
		return Ret.me();
	}
	
	/**
	 * 商品的上下架
	 * @param ids
	 * @param onsale
	 * @return
	 */
	@RequestMapping("/onSale")
	@ResponseBody
	public Ret onSale(String ids,Integer onsale){
		Long[] idArr = StrUtils.splitToLong(ids);
		productCenterService.onSale(idArr,onsale);
		return Ret.me();
	}
}
