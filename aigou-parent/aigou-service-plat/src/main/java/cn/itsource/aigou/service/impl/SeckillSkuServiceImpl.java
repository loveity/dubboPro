package cn.itsource.aigou.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.consts.GlobalSettingNames;
import cn.itsource.aigou.core.consts.bis.JobTypeConsts;
import cn.itsource.aigou.core.consts.bis.SeckillResultStateConsts;
import cn.itsource.aigou.core.domain.SeckillResult;
import cn.itsource.aigou.core.domain.SeckillSku;
import cn.itsource.aigou.core.mapper.SeckillSkuMapper;
import cn.itsource.aigou.core.utils.GlobalSetting;
import cn.itsource.aigou.facade.QuartzService;
import cn.itsource.aigou.facade.query.QuartzJobInfo;
import cn.itsource.aigou.service.ISeckillResultService;
import cn.itsource.aigou.service.ISeckillSkuService;
@Service
public class SeckillSkuServiceImpl extends BaseServiceImpl<SeckillSku> implements ISeckillSkuService{
	@Autowired
	private SeckillSkuMapper mapper;
	
	@Autowired
	private ISeckillResultService seckillResultService;
	
	@Reference
	private QuartzService quartzService;
	
	@Override
	protected BaseMapper<SeckillSku> getMapper() {
		return mapper;
	}

	@Override
	public void hitSuccess(long seckillSkuId, long ssoId) {
		//获取剩余数量
		SeckillSku seckillSku = this.get(seckillSkuId);
		if(seckillSku.getLeftCount() <= 0){
			return ;
		}
		//抢购单品的剩余数量-1
		seckillSku.setLeftCount(seckillSku.getLeftCount() - 1);
		this.updatePart(seckillSku);
		
		//写入抢购成功结果记录
		//用户根据该结果进行订单确认（不减库存），生成成功后流程同商品订单主流程
		SeckillResult seckillResult = new SeckillResult();
		seckillResult.setCreateTime(System.currentTimeMillis());
		seckillResult.setSeckillSkuId(seckillSkuId);
		seckillResult.setSsoId(ssoId);
		seckillResult.setTotalCount(1);
		seckillResult.setState(SeckillResultStateConsts.WAIT_CONFIRM);
		Long expireMinutes = Long.valueOf(GlobalSetting.get(GlobalSettingNames.SYSTEM_CONFIRM_SECKILL_MINUTES));
		seckillResult.setLastConfirmTime(System.currentTimeMillis() + expireMinutes * 60 * 1000);
		seckillResultService.savePart(seckillResult );
		
		//倒计时确认抢购订单过期时间
		QuartzJobInfo endHandleInfo = new QuartzJobInfo();
		endHandleInfo.setFireDate(new Date(seckillResult.getLastConfirmTime()));
		endHandleInfo.setJobName("SECKILL_RESULT_JOB_"+seckillResult.getId());
		Map<String, Object> params = new HashMap<>();
		params.put("resultId", seckillResult.getId());
		endHandleInfo.setParams(params);
		endHandleInfo.setType(JobTypeConsts.SECKILL_CONFIRM_EXPIRE_JOB);
		quartzService.addJob(endHandleInfo);
	}

}
