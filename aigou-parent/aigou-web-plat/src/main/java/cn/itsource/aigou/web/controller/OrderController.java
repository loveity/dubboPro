package cn.itsource.aigou.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itsource.aigou.core.domain.Order;
import cn.itsource.aigou.core.utils.Page;
import cn.itsource.aigou.core.utils.Ret;
import cn.itsource.aigou.core.utils.StrUtils;
import cn.itsource.aigou.facade.UserCenterService;
import cn.itsource.aigou.facade.query.OrderQuery;
import cn.itsource.aigou.web.consts.ControllerConsts;

@Controller
@RequestMapping("/" + OrderController.DOMAIN)
public class OrderController {
	public static final String DOMAIN = "order";

	@Reference
	private UserCenterService userCenterService;

	@RequestMapping(ControllerConsts.URL_INDEX)
	public String index() {
		return DOMAIN + ControllerConsts.VIEW_INDEX;
	}

	@RequestMapping(ControllerConsts.URL_JSON)
	@ResponseBody
	public Page<Order> json(OrderQuery qObject) {
		Page<Order> pageResult = userCenterService.orderQuery(qObject);
		System.out.println(qObject.getKeyword());
		return pageResult;
	}

	@RequestMapping(ControllerConsts.URL_EDIT)
	public String edit(Long id, Model model) {
		if (null != id) {
			
		}
		return DOMAIN + ControllerConsts.VIEW_EDIT;
	}

	/**
	 * 保存或修改订单信息
	 * @param order 订单信息
	 * @return
	 */
	@RequestMapping(ControllerConsts.URL_STORE)
	@ResponseBody
	public Ret store(Order order) {
		
		return Ret.me();
	}

	@RequestMapping(ControllerConsts.URL_SHOW)
	public String show(Long id, Model model) {
		Order o = userCenterService.orderGet(id);
		model.addAttribute("o", o);
		return DOMAIN + ControllerConsts.VIEW_SHOW;
	}

	/**
	 * 订单的商品出库
	 * @param ids
	 * @param shipStore 快递服务商ID
	 * @param shipSn 快递单号
	 * @return
	 */
	@RequestMapping("/sendShip")
	@ResponseBody
	public Ret onSale(String ids,Long shipStore,String shipSn){
		Long[] idArr = StrUtils.splitToLong(ids);
		userCenterService.orderSendShip(idArr,shipStore,shipSn);
		return Ret.me();
	}
}
