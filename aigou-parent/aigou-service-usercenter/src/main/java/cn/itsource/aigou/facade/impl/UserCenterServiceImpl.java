package cn.itsource.aigou.facade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;

import cn.itsource.aigou.core.consts.bis.PayBillBusinessTypeConsts;
import cn.itsource.aigou.core.domain.Order;
import cn.itsource.aigou.core.domain.PayBill;
import cn.itsource.aigou.core.domain.Sku;
import cn.itsource.aigou.core.domain.Sso;
import cn.itsource.aigou.core.domain.VipAddress;
import cn.itsource.aigou.core.domain.VipCart;
import cn.itsource.aigou.core.exception.BisException;
import cn.itsource.aigou.core.utils.Page;
import cn.itsource.aigou.core.utils.Ret;
import cn.itsource.aigou.facade.UserCenterService;
import cn.itsource.aigou.facade.query.OrderQuery;
import cn.itsource.aigou.facade.query.QuartzJobInfo;
import cn.itsource.aigou.service.IOrderService;
import cn.itsource.aigou.service.ISsoService;
import cn.itsource.aigou.service.IVipAddressService;
import cn.itsource.aigou.service.IVipCartService;

@Service
public class UserCenterServiceImpl implements UserCenterService {

	@Autowired
	private ISsoService ssoService;
	
	@Autowired
	private IVipCartService cartService;
	
	@Autowired
	private IVipAddressService addressService;
	
	@Autowired
	private IOrderService orderService;
	
	public Sso getSsoUser(Long id) {
		return ssoService.get(id);
	}

	@Override
	public Sso getSsoByPhone(String phone) {
		return ssoService.getSsoByPhone(phone);
	}

	@Override
	public Ret regUserByPhone(String phone, String password, String smsCaptcha) {
		return ssoService.regUserByPhone(phone, password, smsCaptcha);
	}

	@Override
	public Ret login(String username, String password) {
		return ssoService.login(username,password);
	}

	@Override
	public void cartAdd(Long ssoId,Long skuId, Integer number) {
		cartService.add(ssoId,skuId,number);
	}

	@Override
	public void cartDel(Long ssoId, Long[] cartIdArr) {
		cartService.del(ssoId,cartIdArr);
	}

	@Override
	public Sku cartChangeNumber(Long ssoId, Long cartId, Integer number) {
		return cartService.changeNumber(ssoId,cartId,number);
	}

	@Override
	public void cartSelect(Long ssoId, Long[] cartIdArr) {
		cartService.updateSelectCart(ssoId,cartIdArr);
	}

	@Override
	public void cartQuickBuy(Long ssoId, Long skuId, Integer number) {
		cartService.quickBuy(ssoId,skuId,number);
	}

	@Override
	public Map<String, Object> cartCalculate(Long ssoId) {
		return cartService.caculate(ssoId);
	}

	@Override
	public Map<String, Object> cartInfo(Long ssoId) {
		return cartService.info(ssoId);
	}
	
	@Override
	public Map<String, Object> cartSelectedInfo(Long ssoId) {
		return cartService.selectedInfo(ssoId);
	}

	@Override
	public VipAddress vipAdrressStore(VipAddress address) {
		return addressService.store(address);
	}

	@Override
	public VipAddress vipAddressGet(Long id) {
		return addressService.get(id);
	}

	@Override
	public List<VipAddress> vipAddressesGets(Long ssoId) {
		return addressService.getBySso(ssoId);
	}

	@Override
	public void vipAddressRemove(Long id) {
		addressService.delete(id);
		
	}

	@Override
	public void vipAddressDefaultSet(Long id) {
		addressService.setDefault(id);
	}

	@Override
	public VipAddress vipAddressDefaultGet(Long ssoId) {
		return addressService.getDefault(ssoId);
	}

	@Override
	public Ret orderCreate(Order formOrder, Long addressId, Long billId) {
		return orderService.create(formOrder,addressId,billId);
	}

	@Override
	public void notifyOrderPayed(PayBill payBill) {
		switch (payBill.getBusinessType()) {
			case PayBillBusinessTypeConsts.ORDER_PRODUCT:
				//.通知商品订单支付成功
				orderService.notifyPayed(payBill);
				break;
			case PayBillBusinessTypeConsts.ORDER_REFUND:
				//TODO .通知退款订单支付成功
				break;
			default:
				break;
			}
	}

	@Override
	public Page<Order> orderQuery(OrderQuery query) {
		return orderService.queryPage(query);
	}

	@Override
	public Map<String, Integer> orderStatisBySso(Long ssoId, Integer year) {
		return orderService.statisBySso(ssoId,year);
	}

	@Override
	public Order orderGet(Long id) {
		return orderService.getById(id);
	}

	@Override
	public Order orderGetBySn(String orderSn) {
		return orderService.getBySn(orderSn);
	}

	@Override
	public void orderCancel(Long ssoId, Long orderId) {
		orderService.cancelBySso(ssoId,orderId);
	}
	
	@Override
	public void orderConfirmFinish(Long ssoId, Long orderId) throws BisException {
		orderService.confirmFinishBySso(ssoId,orderId);
	}

	@Override
	public void orderDelete(Long ssoId,Long[] orderIdArr) {
		orderService.deleteBatch(ssoId,orderIdArr);
	}

	@Override
	public void orderCancelByQuartz(QuartzJobInfo info) {
		orderService.cancelByQuartz(Long.parseLong(info.getParams().get("orderId").toString()));
	}

	@Override
	public void orderConfirmByQuartz(QuartzJobInfo info) {
		orderService.confirmFnishByQuartz(Long.parseLong(info.getParams().get("orderId").toString()));
	}

	@Override
	public void orderSendShip(Long[] orderIdArr,Long shipStore,String shipSn) {
		orderService.sendShip(orderIdArr,shipStore,shipSn);
	}
}
