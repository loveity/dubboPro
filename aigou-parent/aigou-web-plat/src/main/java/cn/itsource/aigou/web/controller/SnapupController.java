package cn.itsource.aigou.web.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itsource.aigou.core.consts.ICodes;
import cn.itsource.aigou.core.consts.bis.SeckillStateConsts;
import cn.itsource.aigou.core.domain.Seckill;
import cn.itsource.aigou.core.domain.SeckillSku;
import cn.itsource.aigou.core.domain.Sku;
import cn.itsource.aigou.core.exception.BisException;
import cn.itsource.aigou.core.utils.Page;
import cn.itsource.aigou.core.utils.Ret;
import cn.itsource.aigou.core.utils.StrUtils;
import cn.itsource.aigou.facade.PlatManageService;
import cn.itsource.aigou.facade.ProductCenterService;
import cn.itsource.aigou.facade.query.SeckillQuery;
import cn.itsource.aigou.web.consts.ControllerConsts;

@Controller
@RequestMapping("/" + SnapupController.DOMAIN)
public class SnapupController {
	public static final String DOMAIN = "snapup";

	@Reference
	private PlatManageService platManageService;
	
	@Reference
	private ProductCenterService productCenterService;

	@RequestMapping(ControllerConsts.URL_INDEX)
	public String index() {
		return DOMAIN + ControllerConsts.VIEW_INDEX;
	}

	@RequestMapping(ControllerConsts.URL_JSON)
	@ResponseBody
	public Page<Seckill> json(SeckillQuery qObject) {
		Page<Seckill> pageResult = platManageService.seckillPage(qObject);
		return pageResult;
	}

	@RequestMapping(ControllerConsts.URL_EDIT)
	public String edit(Long id, Model model) {
		if (null != id) {
			Seckill seckill = platManageService.seckillGet(id);
			if(seckill.getState() != SeckillStateConsts.WAIT_PUBLSH){//不是待发布的状态
				throw BisException.me().setInfo("不允许修改");
			}
			model.addAttribute("seckill", seckill);
			StringBuilder skuDatas = new StringBuilder();
			List<SeckillSku> skuList = platManageService.seckillGetSkus(id);
			for (SeckillSku seckillSku : skuList) {
				skuDatas.append(":::").append(seckillSku.getSkuId()).append("::").append(seckillSku.getProductId()).append("::")
				.append(seckillSku.getSkuPic()).append("::").append(seckillSku.getSkuName()).append("::")
				.append(seckillSku.getPrice()).append("::").append(seckillSku.getTotalCount());
			}
			if(skuDatas.length() > 3){
				model.addAttribute("skuDatas", skuDatas.toString().substring(3) );
			}
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			model.addAttribute("beginTimeFormat", dateFormat.format(new Date(seckill.getBeginTime())));
			model.addAttribute("endTimeFormat", dateFormat.format(new Date(seckill.getEndTime())));
		}
		return DOMAIN + ControllerConsts.VIEW_EDIT;
	}

	/**
	 * 保存或修改信息
	 * @param seckill 抢购活动信息
	 * @param skuDatas 抢购单品数据
	 * @return
	 */
	@RequestMapping(ControllerConsts.URL_STORE)
	@ResponseBody
	public Ret store(Seckill seckill,String skuDatas,String beginTimeFormat,String endTimeFormat) {
		List<SeckillSku> seckillSkuList = null;
		if(StringUtils.isNotBlank(skuDatas)){
			seckillSkuList = new ArrayList<>();
			String[] skusArr = skuDatas.split(":::");
			for (String skus : skusArr) {
				String[] skuArr = skus.split("::");
				if(skuArr.length == 6){
					Long skuId = Long.parseLong(skuArr[0]);
					Long productId = Long.parseLong(skuArr[1]);
					String skuMainPic = skuArr[2];
					String skuTitle = skuArr[3];
					Integer skuPrice = Integer.parseInt(skuArr[4]);
					Integer skuAmount = Integer.parseInt(skuArr[5]);
					
					SeckillSku sku = new SeckillSku();
					sku.setCreateTime(System.currentTimeMillis());
					sku.setUpdateTime(sku.getCreateTime());
					sku.setLeftCount(skuAmount);
					sku.setPrice(skuPrice);
					sku.setProductId(productId);
					sku.setSkuId(skuId);
					sku.setSkuName(skuTitle);
					sku.setSkuPic(skuMainPic);
					sku.setTotalCount(skuAmount);
					seckillSkuList.add(sku);
				}
			}
		}
		
		seckill.setSeckillSkuList(seckillSkuList );
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			seckill.setBeginTime(dateFormat.parse(beginTimeFormat).getTime());
			seckill.setEndTime(dateFormat.parse(endTimeFormat).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		platManageService.seckillStore(seckill);
		return Ret.me();
	}

	@RequestMapping(ControllerConsts.URL_SHOW)
	public String show(Long id, Model model) {
		Seckill o = platManageService.seckillGet(id);
		model.addAttribute("o", o);
		return DOMAIN + ControllerConsts.VIEW_SHOW;
	}
	
	/**
	 * 根据sku code获取sku信息
	 * @param skuCode
	 * @return
	 */
	@RequestMapping("/getSku")
	@ResponseBody
	public Sku getSku(String skuCode){
		Sku sku = productCenterService.getSkuByCode(skuCode);
		return sku;
	}
	
	/**
	 * 发布秒杀活动
	 * 发布后不能更改活动，进入活动 初始化 和 开启 倒计时
	 * @param ids
	 * @return
	 */
	@RequestMapping("/publish")
	@ResponseBody
	public Ret publish(String ids){
		if(StringUtils.isBlank(ids)){
			throw BisException.me().setCode(ICodes.ILLEGAL_ACCESS);
		}
		Long[] seckillIdArr = StrUtils.splitToLong(ids);
		platManageService.seckillPublish(seckillIdArr);
		return Ret.me();
	}
	
}
