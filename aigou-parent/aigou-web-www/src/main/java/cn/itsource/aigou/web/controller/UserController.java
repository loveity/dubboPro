package cn.itsource.aigou.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.org.apache.bcel.internal.generic.ReturnaddressType;

import cn.itsource.aigou.core.consts.GlobalSettingNames;
import cn.itsource.aigou.core.consts.ICodes;
import cn.itsource.aigou.core.consts.bis.BooleanConsts;
import cn.itsource.aigou.core.domain.Order;
import cn.itsource.aigou.core.domain.PayBill;
import cn.itsource.aigou.core.domain.Sku;
import cn.itsource.aigou.core.domain.VipAddress;
import cn.itsource.aigou.core.domain.VipCart;
import cn.itsource.aigou.core.exception.BisException;
import cn.itsource.aigou.core.utils.GlobalSetting;
import cn.itsource.aigou.core.utils.Ret;
import cn.itsource.aigou.core.utils.StrUtils;
import cn.itsource.aigou.facade.CommonService;
import cn.itsource.aigou.facade.PayCenterService;
import cn.itsource.aigou.facade.PlatManageService;
import cn.itsource.aigou.facade.UserCenterService;
import cn.itsource.aigou.web.utils.SsoContext;

@Controller
@RequestMapping("/user")
public class UserController {

	@Reference
	private UserCenterService userCenterService;

	@Reference
	private PlatManageService platManageService;
	
	@Reference
	private CommonService commonService;

	@Reference
	private PayCenterService payCenterService;

	/**
	 * 购物车主页面
	 * 
	 * @return
	 */
	@RequestMapping("/cart")
	public String cart(Model model) {
		Map<String, Object> cartInfoMap = userCenterService.cartInfo(SsoContext.getSsoId());
		model.addAttribute("cartInfo", cartInfoMap);
		return "cart";
	}

	/**
	 * 添加SKU到购物车
	 * 
	 * @param skuId
	 *            skuId
	 * @param number
	 *            数量
	 * @return
	 */
	@RequestMapping("/cart/add")
	@ResponseBody
	public Ret cartAdd(Long skuId, Integer number) {
		userCenterService.cartAdd(SsoContext.getSsoId(), skuId, number);
		return Ret.me();
	}

	/**
	 * 删除购物车指定的购物条目
	 * 
	 * @param cartIds
	 * @return
	 */
	@RequestMapping("/cart/del")
	@ResponseBody
	public Ret cartDel(String cartIds) {
		userCenterService.cartDel(SsoContext.getSsoId(), StrUtils.splitToLong(cartIds));
		Map<String, Object> calResultMap = userCenterService.cartCalculate(SsoContext.getSsoId());
		return Ret.me().setData(calResultMap);
	}

	@RequestMapping("/cart/remove")
	@ResponseBody
	public Ret cartRemove(String cartIds) {
		userCenterService.cartDel(SsoContext.getSsoId(), StrUtils.splitToLong(cartIds));
		return Ret.me();
	}

	/**
	 * 修改购物车中商品的数量
	 * 
	 * @param cartId
	 * @param number
	 * @return
	 */
	@RequestMapping("/cart/changeNumber")
	@ResponseBody
	public Ret changeCartNumber(Long cartId, Integer number) {
		Sku sku = userCenterService.cartChangeNumber(SsoContext.getSsoId(), cartId, number);
		Map<String, Object> calResultMap = userCenterService.cartCalculate(SsoContext.getSsoId());
		Integer marketPrice = sku.getMarketPrice();
		Integer price = sku.getPrice();
		Integer total = price * number;
		calResultMap.put("marketPrice", marketPrice);
		calResultMap.put("price", price);
		calResultMap.put("subTotal", total);
		return Ret.me().setData(calResultMap);
	}

	/**
	 * 选中多个购物车记录
	 * 
	 * @param cartIds
	 *            1,2,3,4
	 * @return
	 */
	@RequestMapping("/cart/select")
	@ResponseBody
	public Ret cartSelect(String cartIds) {
		userCenterService.cartSelect(SsoContext.getSsoId(), StrUtils.splitToLong(cartIds));
		Map<String, Object> calResultMap = userCenterService.cartCalculate(SsoContext.getSsoId());
		return Ret.me().setData(calResultMap);
	}

	/**
	 * 马上购买
	 * 
	 * @param skuId
	 *            skuId
	 * @param number
	 *            数量
	 * @return
	 */
	@RequestMapping("/cart/quickBuy")
	@ResponseBody
	public Ret cartQuickBuy(Long skuId, Integer number) {
		// 加入购物车，并取消所有选中，默认选中该条记录
		userCenterService.cartQuickBuy(SsoContext.getSsoId(), skuId, number);
		return Ret.me();
	}

	/**
	 * 购物车结算检查
	 * 
	 * @return
	 */
	@RequestMapping("/cart/checkout")
	@ResponseBody
	public Ret cartCheckOut() {
		Map<String, Object> calculate = userCenterService.cartCalculate(SsoContext.getSsoId());
		Object selectedGoodsNumber = calculate.get("selectedGoodsNumber");
		if (null == selectedGoodsNumber || Integer.parseInt(selectedGoodsNumber.toString()) == 0) {
			return Ret.me().setSuccess(false).setInfo("请选择商品进行结算");
		}
		return Ret.me();
	}

	/**
	 * 确认订单页面 接收选择的购物车条目ID
	 * 
	 * @return
	 */
	@RequestMapping("/order/confirm")
	public String orderConfirm(Model model) {

		List<VipAddress> addressList = userCenterService.vipAddressesGets(SsoContext.getSsoId());
		model.addAttribute("addrList", addressList);

		Map<String, Object> selectedCartInfo = userCenterService.cartSelectedInfo(SsoContext.getSsoId());
		model.addAttribute("selectedCartInfo", selectedCartInfo);
		if(selectedCartInfo.get("selectedGoodsNumber").toString().equals("0")){//没有结算项目
			return "redirect:/user/cart";
		}
		return "order.confirm";
	}

	/**
	 * 收藏/取消收藏 商品 goods_ids,sku_id 收藏/取消收藏 商户 shop_id
	 * 
	 * @return 如果收藏成功返回 data=1，如果取消收藏成功返回data=0
	 */
	@RequestMapping("/collect/add")
	@ResponseBody
	public Ret addCollect(Long goods_ids, Long sku_id, Long shop_id) {

		return Ret.me();
	}

	/**
	 * 加载添加/修改地址表单
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/address/form")
	public String addressForm(Long id, Model model) {
		if (null != id) {
			model.addAttribute("address", userCenterService.vipAddressGet(id));
		}
		return "addressForm";
	}

	/**
	 * 存储收货地址
	 * 
	 * @param address
	 * @return
	 */
	@RequestMapping("/address/store")
	@ResponseBody
	public Ret addressStore(VipAddress address) {
		address.setSsoId(SsoContext.getSsoId());
		String areaFullName = platManageService.getRegionFullName(address.getAreaCode());
		String fullAddress = areaFullName + " " + address.getAddress();
		address.setFullAddress(fullAddress);
		address = userCenterService.vipAdrressStore(address);
		return Ret.me().setData(address);
	}

	/**
	 * 删除收货地址
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/address/del")
	@ResponseBody
	public Ret addressDel(Long id) {
		VipAddress existAddress = userCenterService.vipAddressGet(id);
		if (existAddress.getDefaultAddress().intValue() == BooleanConsts.YES) {
			throw BisException.me().setInfo("默认地址不能删除");
		}
		userCenterService.vipAddressRemove(id);
		return Ret.me().setData(id);
	}

	/**
	 * 设置默认收货地址
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/address/setDefault")
	@ResponseBody
	public Ret addressDefaultSet(Long id) {
		userCenterService.vipAddressDefaultSet(id);
		return Ret.me().setData(id);
	}

	/**
	 * 提交订单
	 * 
	 * @param order
	 * @param addressId
	 *            地址id
	 * @param billId
	 *            发票id
	 * @return
	 */
	@RequestMapping("/order/submit")
	@ResponseBody
	public Ret orderSubmit(Order formOrder, Long addressId, Long billId) {
		formOrder.setSsoId(SsoContext.getSsoId());
	
		// 创建订单
		Ret ret = userCenterService.orderCreate(formOrder, addressId, billId);
		if (!ret.isSuccess())
			return ret;
		Order order = (Order) ret.getData();

		// 通过订单创建支付单
		PayBill payBill = payCenterService.orderPayBillCreate(order);

		// 返回支付单并跳转到支付中心
		String payUrl = GlobalSetting.get(GlobalSettingNames.URL_PAY)
				+ GlobalSetting.get(GlobalSettingNames.URL_PAY_GATEWAY) + "?sn=" + payBill.getUnionPaySn();
		return Ret.me().setData(payUrl);
	}
	
	@RequestMapping("/seckill/hit")
	@ResponseBody
	public Ret seckillHit(Long secId,Long skuId,String code){
		if(null==skuId||secId==null||StringUtils.isBlank(code)){
			throw BisException.me().setCode(ICodes.ILLEGAL_ACCESS);
		}
		
		String storeedCode = commonService.redisGet("seckill-rn-" + secId);
		if(StringUtils.isBlank(storeedCode)){
			throw BisException.me().setInfo("抢购失败，请刷新页面重新抢购");
		}
		
		if(!code.equals(storeedCode)){
			throw BisException.me().setInfo("抢购还未开始或已结束");
		}
		
		String num = commonService.redisGet("seckill-num-" + skuId);
		System.out.println("num == "+num);
		if(StringUtils.isBlank(num)){
			throw BisException.me().setInfo("抢购还未开始或已结束");
		}
		if(Integer.parseInt(num) <= 0){
			throw BisException.me().setInfo("抢购失败，已被抢光啦！");
		}
		
		//进入抢购成功队列
		boolean isSuccess = platManageService.inSechitQueue(SsoContext.getSsoId(),skuId);
		if(!isSuccess){
			throw BisException.me().setInfo("抢购失败");
		}
		
		return Ret.me();
	}
}
