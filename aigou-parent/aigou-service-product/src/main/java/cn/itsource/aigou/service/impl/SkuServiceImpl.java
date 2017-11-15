package cn.itsource.aigou.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.consts.bis.ProductStateConsts;
import cn.itsource.aigou.core.domain.Product;
import cn.itsource.aigou.core.domain.ProductViewProperty;
import cn.itsource.aigou.core.domain.Sku;
import cn.itsource.aigou.core.domain.SkuProperty;
import cn.itsource.aigou.core.mapper.SkuMapper;
import cn.itsource.aigou.facade.CommonService;
import cn.itsource.aigou.service.IProductService;
import cn.itsource.aigou.service.ISkuService;

@Service
public class SkuServiceImpl extends BaseServiceImpl<Sku> implements ISkuService {
	@Autowired
	private SkuMapper mapper;

	@Autowired
	private IProductService productService;

	@Reference
	private CommonService commonService;

	@Override
	protected BaseMapper<Sku> getMapper() {
		return mapper;
	}

	@Override
	public void saveSku(Sku sku) {
		// 获取sku的属性集合格式字符串
		// propId:propName:optionId:optionName(value)_propId:propName:optionId:optionName(value)
		String skuProperties = "0:规格:0:"+sku.getSkuName();
		// sku的属性
		sku.setSkuProperties(skuProperties);

		if (null != sku.getId()) {
			sku.setUpdateTime(System.currentTimeMillis());
			mapper.updatePart(sku);
		} else {// 新增
			String maxCode = mapper.getMaxCode(sku.getProductId());
			String seq = "01";
			if (StringUtils.isNotBlank(maxCode)) {
				seq = maxCode.substring(maxCode.length() - 2);
				int seqNumber = Integer.parseInt(seq);
				seqNumber++;
				if (seqNumber <= 9) {
					seq = "0" + seqNumber;
				} else {
					seq = "" + seqNumber;
				}
			}
			Product product = productService.get(sku.getProductId());
			if (null != product) {
				sku.setSkuCode(product.getCode() + seq);
			}
			if (null == (sku.getAvailableStock())) {
				sku.setAvailableStock(0);
			}
			if (null == sku.getFrozenStock()) {
				sku.setFrozenStock(0);
			}
			if (null == sku.getSortIndex()) {
				sku.setSortIndex(100);
			}
			sku.setSaleCount(0);
			sku.setCreateTime(System.currentTimeMillis());
			sku.setUpdateTime(System.currentTimeMillis());
			mapper.save(sku);
		}

		// 更新对应商品的价格区间
		mapper.updateProductPrice(sku.getProductId());

		// 更新到es
		Product product = productService.get(sku.getProductId());
		if(product.getState()==ProductStateConsts.PRODUCT_STATE_ONSALE){
			commonService.saveOrUpdateProduct2Es(sku.getProductId().toString(),
					JSONObject.toJSONString(product));
		}
	}

	@Override
	public void recoverStock(Long skuId, Integer amount) {
		mapper.recoverStock(skuId,amount);
	}

	@Override
	public void outbound(Long skuId, Integer amount) {
		mapper.outbound(skuId,amount);
	}

	@Override
	public Sku getByCode(String skuCode) {
		return mapper.getByCode(skuCode);
	}
}
