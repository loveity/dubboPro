package cn.itsource.aigou.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.consts.bis.JobTypeConsts;
import cn.itsource.aigou.core.consts.bis.SeckillResultStateConsts;
import cn.itsource.aigou.core.consts.bis.SeckillStateConsts;
import cn.itsource.aigou.core.domain.Seckill;
import cn.itsource.aigou.core.domain.SeckillResult;
import cn.itsource.aigou.core.domain.SeckillSku;
import cn.itsource.aigou.core.domain.Sku;
import cn.itsource.aigou.core.mapper.SeckillMapper;
import cn.itsource.aigou.core.utils.StrUtils;
import cn.itsource.aigou.facade.CommonService;
import cn.itsource.aigou.facade.ProductCenterService;
import cn.itsource.aigou.facade.QuartzService;
import cn.itsource.aigou.facade.query.QuartzJobInfo;
import cn.itsource.aigou.mq.ActivemqQueueConsumerAsyn;
import cn.itsource.aigou.mq.listener.SeckillDealListener;
import cn.itsource.aigou.service.ActiveMqService;
import cn.itsource.aigou.service.ISeckillResultService;
import cn.itsource.aigou.service.ISeckillService;
import cn.itsource.aigou.service.ISeckillSkuService;

@Service
public class SeckillServiceImpl extends BaseServiceImpl<Seckill> implements ISeckillService {
	@Autowired
	private SeckillMapper mapper;

	@Reference
	private QuartzService quartzService;

	@Autowired
	private ActiveMqService mqService;

	@Autowired
	private ISeckillSkuService seckillSkuService;

	@Autowired
	private ISeckillResultService seckillResultService;

	@Reference
	private CommonService commonService;

	@Reference
	private ProductCenterService productCenterService;

	private Map<String, ActivemqQueueConsumerAsyn> queueConsumerMap = new HashMap<>();

	@Override
	protected BaseMapper<Seckill> getMapper() {
		return mapper;
	}

	@Override
	public void saveOrUpdate(Seckill seckill) {
		seckill.setUpdateTime(System.currentTimeMillis());
		if (seckill.getId() != null) {
			this.updatePart(seckill);
		} else {
			seckill.setState(SeckillStateConsts.WAIT_PUBLSH);
			seckill.setCreateTime(System.currentTimeMillis());
			this.savePart(seckill);
		}

		if (seckill.getId() != null && seckill.getSeckillSkuList() != null && seckill.getSeckillSkuList().size() > 0) {
			List<SeckillSku> seckillSkuList = seckill.getSeckillSkuList();
			for (SeckillSku seckillSku : seckillSkuList) {
				seckillSku.setSeckillId(seckill.getId());
			}
			mapper.deleteBySecKillId(seckill.getId());
			mapper.saveSkuList(seckillSkuList);
		}

	}

	@Override
	public List<SeckillSku> getSkuList(Long seckillId) {
		return mapper.getSkuList(seckillId);
	}

	@Override
	public void publish(Long[] seckillIdArr) {
		// 秒杀活动发布逻辑
		for (Long seckillId : seckillIdArr) {
			Seckill seckill = this.get(seckillId);
			// 冻结本次活动所需要的单品库存
			List<SeckillSku> skuList = this.getSkuList(seckillId);
			for (SeckillSku seckillSku : skuList) {
				Long skuId = seckillSku.getSkuId();
				Sku sku = productCenterService.getSku(skuId);
				sku.setAvailableStock(sku.getAvailableStock() - seckillSku.getTotalCount());
				sku.setFrozenStock(sku.getFrozenStock() + seckillSku.getTotalCount());
				productCenterService.updateSku(sku);
			}

			// 修改活动状态为待开启
			seckill.setState(SeckillStateConsts.WAIT_BEGIN);
			this.updatePart(seckill);

			// 创建活动开始预处理任务:开始前1分钟（创建抢购商品的队列(名字规则：seckill-{seckillSkuId})，绑定消费监听者）
			QuartzJobInfo preHandleInfo = new QuartzJobInfo();
			preHandleInfo.setFireDate(new Date(seckill.getBeginTime() - 1 * 60 * 1000));
			preHandleInfo.setJobName("SECKILL_PRE_JOB_" + seckillId);
			Map<String, Object> params = new HashMap<>();
			params.put("seckillId", seckillId);
			preHandleInfo.setParams(params);
			preHandleInfo.setType(JobTypeConsts.SECKILL_PRE_JOB);
			quartzService.addJob(preHandleInfo);

			// 创建活动开始倒计时定时任务：开始时间前1秒（创建 随机的单品抢购接口码，创建单品抢购有效标志）
			QuartzJobInfo beginHandleInfo = new QuartzJobInfo();
			beginHandleInfo.setFireDate(new Date(seckill.getBeginTime() - 1000));
			beginHandleInfo.setJobName("SECKILL_BEGIN_JOB_" + seckillId);
			params = new HashMap<>();
			params.put("seckillId", seckillId);
			beginHandleInfo.setParams(params);
			beginHandleInfo.setType(JobTypeConsts.SECKILL_BEGIN_JOB);
			quartzService.addJob(beginHandleInfo);

			// 创建活动结束倒计时定时任务（删除单品抢购队列，整个活动状态置为已结束）
			QuartzJobInfo endHandleInfo = new QuartzJobInfo();
			endHandleInfo.setFireDate(new Date(seckill.getEndTime()));
			endHandleInfo.setJobName("SECKILL_END_JOB_" + seckillId);
			params = new HashMap<>();
			params.put("seckillId", seckillId);
			endHandleInfo.setParams(params);
			endHandleInfo.setType(JobTypeConsts.SECKILL_END_JOB);
			quartzService.addJob(endHandleInfo);
		}

		// 静态化已发布活动的数据（只静态化已发布待开始状态的活动数据）
		List<Seckill> shownSeckillList = mapper.getByState(SeckillStateConsts.DOING);
		List<Seckill> waitSeckillList = mapper.getByState(SeckillStateConsts.WAIT_BEGIN);
		shownSeckillList.addAll(waitSeckillList);
		for (Seckill seckill : shownSeckillList) {
			List<SeckillSku> skuList = this.getSkuList(seckill.getId());
			seckill.setSeckillSkuList(skuList);
		}

		Map<String, String> nameMap = new HashMap<>();
		String seckillJsonName = StrUtils.getComplexRandomString(16);
		nameMap.put("f", seckillJsonName);

		String seckillJson = JSONObject.toJSONString(shownSeckillList);

		commonService.uploadToQiniuCdn(JSONObject.toJSONString(nameMap).getBytes(), "secname.json", null);
		commonService.uploadToQiniuCdn(seckillJson.getBytes(), seckillJsonName + ".json", null);
	}

	@Override
	public void preHandle(QuartzJobInfo info) {
		// 创建抢购商品的队列(名字规则：seckill-{seckillSkuId})，绑定消费监听者
		Long seckillId = Long.parseLong(info.getParams().get("seckillId").toString());
		List<SeckillSku> skuList = this.getSkuList(seckillId);
		for (SeckillSku seckillSku : skuList) {
			String queueName = "seckill-" + seckillSku.getId();
			try {
				ActivemqQueueConsumerAsyn queueListner = mqService.createQueueAndListener(queueName,
						new SeckillDealListener(seckillSkuService, commonService));
				queueConsumerMap.put(queueName, queueListner);
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void beginHandle(QuartzJobInfo info) {
		// 创建 随机的抢购接口码（自动过期），创建单品抢购的最大数量（自动过期），设置活动状态为开启
		Long seckillId = Long.parseLong(info.getParams().get("seckillId").toString());
		Seckill seckill = this.get(seckillId);
		List<SeckillSku> skuList = this.getSkuList(seckillId);
		// 设置活动抢购随机码
		int expireSeconds = (int) ((seckill.getEndTime() - seckill.getBeginTime()) * 0.001);
		commonService.redisSetex("seckill-rn-" + seckillId, expireSeconds, StrUtils.getComplexRandomString(32));
		for (SeckillSku seckillSku : skuList) {
			commonService.redisSetex("seckill-num-" + seckillSku.getId(), expireSeconds,
					seckillSku.getTotalCount() + "");
		}
		// 设置活动状态为开启
		seckill.setState(SeckillStateConsts.DOING);
		seckill.setUpdateTime(System.currentTimeMillis());
		this.updatePart(seckill);
	}

	@Override
	public void endHandle(QuartzJobInfo info) {
		// 清空抢购接口码，删除单品抢购队列，整个活动状态置为已结束
		Long seckillId = Long.parseLong(info.getParams().get("seckillId").toString());
		// 删除抢购接口码
		commonService.redisDel("seckill-rn-" + seckillId);

		List<SeckillSku> skuList = this.getSkuList(seckillId);
		// 删除单品抢购队列
		for (SeckillSku seckillSku : skuList) {
			String queueName = "seckill-" + seckillSku.getId();
			try {
				ActivemqQueueConsumerAsyn queueConsumerAsyn = queueConsumerMap.get(queueName);
				queueConsumerAsyn.close();
				queueConsumerMap.remove(queueName);
				mqService.deleteQueue(queueName);
			} catch (JMSException e) {
				e.printStackTrace();
			}
			// 返还所有未被抢购完的单品库存[冻结->可用]
			if (seckillSku.getLeftCount() > 0) {
				Long skuId = seckillSku.getSkuId();
				Sku sku = productCenterService.getSku(skuId);
				sku.setAvailableStock(sku.getAvailableStock() - seckillSku.getLeftCount());
				sku.setFrozenStock(sku.getFrozenStock() + seckillSku.getLeftCount());
				productCenterService.updateSku(sku);
			}
		}

		// 更新秒杀活动状态为已结束
		Seckill seckill = this.get(seckillId);
		seckill.setState(SeckillStateConsts.END);
		seckill.setUpdateTime(System.currentTimeMillis());
		this.updatePart(seckill);
	}

	@Override
	public void expireResult(QuartzJobInfo info) {
		Long resultId = Long.parseLong(info.getParams().get("resultId").toString());
		// 获取抢购结果状态
		SeckillResult seckillResult = seckillResultService.get(resultId);
		if(null==seckillResult) return;
		if (seckillResult.getState() != SeckillResultStateConsts.WAIT_CONFIRM) {
			return;
		}
		// 修改结果状态为 取消
		seckillResult.setState(SeckillResultStateConsts.FAIL);
		seckillResultService.updatePart(seckillResult);
		// 直接返还库存[冻结->可用]
		SeckillSku seckillSku = seckillSkuService.get(seckillResult.getSeckillSkuId());
		Long skuId = seckillSku.getSkuId();
		Sku sku = productCenterService.getSku(skuId);
		sku.setAvailableStock(sku.getAvailableStock() - seckillResult.getTotalCount());
		sku.setFrozenStock(sku.getFrozenStock() + seckillResult.getTotalCount());
		productCenterService.updateSku(sku);
	}

	@Override
	public boolean inHitQueue(Long ssoId, Long seckillSkuId) {
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("seckillSkuId", seckillSkuId);
		message.put("ssoId", ssoId);
		String queueName = "seckill-" + seckillSkuId;
		try {
			mqService.sendQueueMessage(queueName, message);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
