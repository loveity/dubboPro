package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.consts.bis.RegChannelConsts;
import cn.itsource.aigou.core.domain.Sso;
import cn.itsource.aigou.core.domain.VipBase;
import cn.itsource.aigou.core.mapper.VipBaseMapper;
import cn.itsource.aigou.service.IVipBaseService;
@Service
public class VipBaseServiceImpl extends BaseServiceImpl<VipBase> implements IVipBaseService{
	@Autowired
	private VipBaseMapper mapper;
	
	@Override
	protected BaseMapper<VipBase> getMapper() {
		return mapper;
	}

	@Override
	public void create(Sso sso,Byte channel) {
		VipBase vipBase = new VipBase();
		vipBase.setActiveTime(System.currentTimeMillis());
		vipBase.setActiveType(channel);
		vipBase.setCreateTime(System.currentTimeMillis());
		vipBase.setUpdateTime(System.currentTimeMillis());
		vipBase.setGrowScore(1);
		vipBase.setRegChannel(channel);
		vipBase.setRegTime(sso.getCreateTime());
		vipBase.setSsoId(sso.getId());
		mapper.savePart(vipBase);
	}
}
