package cn.itsource.aigou.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.consts.bis.BooleanConsts;
import cn.itsource.aigou.core.domain.Sku;
import cn.itsource.aigou.core.domain.VipCart;
import cn.itsource.aigou.core.mapper.VipCartMapper;
import cn.itsource.aigou.core.utils.StrUtils;
import cn.itsource.aigou.facade.CommonService;
import cn.itsource.aigou.facade.ProductCenterService;
import cn.itsource.aigou.service.IVipCartService;
@Service
public class VipCartServiceImpl extends BaseServiceImpl<VipCart> implements IVipCartService{
	@Autowired
	private VipCartMapper mapper;
	
	@Reference
	private ProductCenterService productCenterService;
	
	@Reference
	private CommonService commonService;
	
	@Override
	protected BaseMapper<VipCart> getMapper() {
		return mapper;
	}

	@Override
	public void add(Long ssoId, Long skuId, Integer number) {
		
		//查询购物车是否存在
		VipCart existVipCart = mapper.getBySsoSku(ssoId,skuId);
		//存在则增加数量
		if(null!=existVipCart){
			existVipCart.setAmount(existVipCart.getAmount()+number);
			existVipCart.setSelected(BooleanConsts.YES);
			mapper.updatePart(existVipCart);
			return ;
		}
		
		
		//获取数据库最新的sku信息
		Sku sku = productCenterService.getSku(skuId);
		
		Long productId = sku.getProductId();
		//获取es中商品的信息
		Map<String, Object> productMap = commonService.getEsProduct(productId.toString());
		
		/*Object images = productMap.get("images");
		System.out.println(images.getClass());//ArrayList*/
		
		//获取展示主图
		String mainPic = sku.getSkuMainPic();
		if(StringUtils.isBlank(mainPic)){
			String[] imageArr = (String[])productMap.get("images");
			mainPic = imageArr[0];
		}
		//创建购物车条目并保存到数据库
		VipCart vipCart = new VipCart();
		vipCart.setSkuMainPic(mainPic);
		vipCart.setSkuProperties(sku.getSkuName());
		vipCart.setProductId(productId);
		vipCart.setName(productMap.get("name")+"");
		vipCart.setAmount(number);
		vipCart.setCreateTime(System.currentTimeMillis());
		vipCart.setSelected(BooleanConsts.YES);
		vipCart.setSkuId(skuId);
		vipCart.setSsoId(ssoId);
		vipCart.setStoreId(1L);
		vipCart.setStoreName("爱购网官方一号店");
		
		mapper.save(vipCart);
	}

	@Override
	public void del(Long ssoId, Long[] cartIdArr) {
		if(null!=cartIdArr && cartIdArr.length>0){
			mapper.delCartByUser(ssoId,cartIdArr);
		}
	}

	@Override
	public Sku changeNumber(Long ssoId, Long cartId, Integer number) {
		mapper.changeNumber(ssoId,cartId,number);
		VipCart vipCart = this.get(cartId);
		return productCenterService.getSku(vipCart.getSkuId());
	}

	@Override
	public void updateSelectCart(Long ssoId, Long[] cartIdArr) {
		if(null!=cartIdArr && cartIdArr.length>0){
			mapper.updateSelectCart(ssoId,cartIdArr);
		}else{
			mapper.cancelSelectAll(ssoId);
		}
	}

	@Override
	public void quickBuy(Long ssoId, Long skuId, Integer number) {
		//取消所有选中的购物车
		mapper.cancelSelectAll(ssoId);
		//查询是否有相同sku的购物条目
		VipCart existCart = mapper.getBySsoSku(ssoId, skuId);
		System.out.println("existCart="+existCart);
		if(null==existCart){//不存在添加一项
			this.add(ssoId, skuId, number);
		}else{
			//已存在，更新购物车数量为马上购买的数量，选中该条项目
			existCart.setAmount(number);
			existCart.setSelected(BooleanConsts.YES);
			mapper.updatePart(existCart);
		}
	}

	@Override
	public Map<String, Object> caculate(Long ssoId) {
		return this.statistic(this.getCarts(ssoId));
	}
	
	@Override
	public List<VipCart> getCarts(Long ssoId) {
		List<VipCart> carts =  mapper.getCarts(ssoId);
		for (VipCart vipCart : carts) {
			vipCart.setSku(productCenterService.getSku(vipCart.getSkuId()));
		}
		return carts;
	}
	
	@Override
	public Map<String, Object> statistic(List<VipCart> cartList) {
		HashMap<String, Object> calMap = new HashMap<>();
		int goodsNumber = 0;
		int goodsTotalPrice = 0;
		int selectedGoodsNumber = 0;
		int selectedGoodsTotalPrice = 0;
		if(null!=cartList){
			for (VipCart vipCart : cartList) {
				goodsNumber+=vipCart.getAmount();
				int subTotal = vipCart.getSku().getPrice() * vipCart.getAmount();
				goodsTotalPrice+= subTotal;
				if(vipCart.getSelected()==BooleanConsts.YES){
					selectedGoodsNumber+=vipCart.getAmount();
					selectedGoodsTotalPrice+=subTotal;
				}
			}
		}
		
		calMap.put("goodsNumber", goodsNumber);
		calMap.put("goodsTotalPrice", goodsTotalPrice);
		calMap.put("selectedGoodsNumber", selectedGoodsNumber);
		calMap.put("selectedGoodsTotalPrice", selectedGoodsTotalPrice);
		return calMap;
	}
	
	@Override
	public Map<String, Object> info(Long ssoId) {
		List<VipCart> cartList = this.getCarts(ssoId);
		Map<String, Object> infoMap = this.statistic(cartList);
		
		infoMap.put("data", cartList);
		return infoMap;
	}
	
	@Override
	public Map<String, Object> selectedInfo(Long ssoId) {
		List<VipCart> selectedList = new ArrayList<>();
		List<VipCart> cartList = this.getCarts(ssoId);
		for (VipCart vipCart : cartList) {
			if(vipCart.getSelected() == BooleanConsts.YES){
				selectedList.add(vipCart);
			}
		}
		Map<String, Object> infoMap = this.statistic(cartList);
		
		infoMap.put("data", selectedList);
		return infoMap;
	}

	@Override
	public void clearBuy(Long ssoId) {
		mapper.clearBuy(ssoId);
	}

}
