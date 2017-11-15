package cn.itsource.aigou.mq.listener;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import cn.itsource.aigou.facade.CommonService;
import cn.itsource.aigou.service.ISeckillSkuService;

public class SeckillDealListener implements MessageListener{
	
	private ISeckillSkuService seckillSkuService;
	private CommonService commonService;
	
	@Override
	public void onMessage(Message message) {
		MapMessage mapMessage = (MapMessage)message;
		long seckillSkuId = 0;
		long ssoId = 0;
		try {
			seckillSkuId = mapMessage.getLong("seckillSkuId");
			ssoId = mapMessage.getLong("ssoId");
		} catch (JMSException e) {
			e.printStackTrace();
		}
		if(seckillSkuId<=0 || ssoId<=0){
			return;
		}
		
		//判断可抢购量是否大于0
		String key = "seckill-num-"+seckillSkuId;
		String leftNum = commonService.redisGet(key);
		if(leftNum.equals("0")){
			return ;
		}
		//可抢购量-1
		commonService.redisDecr(key);
		
		//按顺序记录抢购资格成功的用户
		System.out.println("ssoId="+ssoId+",成功抢到了seckillSkuId="+seckillSkuId);
		seckillSkuService.hitSuccess(seckillSkuId,ssoId);
	}

	public SeckillDealListener() {
	}

	public SeckillDealListener(ISeckillSkuService seckillSkuService,CommonService commonService) {
		this.seckillSkuService = seckillSkuService;
		this.commonService = commonService;
	}

	public ISeckillSkuService getSeckillSkuService() {
		return seckillSkuService;
	}

	public void setSeckillSkuService(ISeckillSkuService seckillSkuService) {
		this.seckillSkuService = seckillSkuService;
	}
	
}
