package cn.itsource.aigou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.consts.bis.BooleanConsts;
import cn.itsource.aigou.core.domain.VipAddress;
import cn.itsource.aigou.core.mapper.VipAddressMapper;
import cn.itsource.aigou.service.IVipAddressService;
@Service
public class VipAddressServiceImpl extends BaseServiceImpl<VipAddress> implements IVipAddressService{
	@Autowired
	private VipAddressMapper mapper;
	
	@Override
	protected BaseMapper<VipAddress> getMapper() {
		return mapper;
	}

	@Override
	public VipAddress store(VipAddress address) {
		address.setUpdateTime(System.currentTimeMillis());
		address.setDefaultAddress((byte)(BooleanConsts.NO));
		//判断是否是存在默认的地址，不存在则该条地址为默认
		VipAddress existDefaultAddr = this.getDefault(address.getSsoId());
		if(address.getId()==null){
			if(existDefaultAddr==null){
				address.setDefaultAddress((byte)(BooleanConsts.YES));
			}
			address.setCreateTime(System.currentTimeMillis());
			this.savePart(address);
		}else{
			if(existDefaultAddr!=null && existDefaultAddr.getId().longValue() == address.getId()){
				address.setDefaultAddress((byte)(BooleanConsts.YES));
			}
			this.updatePart(address);
		}
		return address;
	}

	@Override
	public List<VipAddress> getBySso(Long ssoId) {
		return mapper.getBySso(ssoId);
	}

	@Override
	public void setDefault(Long id) {
		VipAddress vipAddress = this.get(id);
		//取消用户当前的默认地址
		mapper.cancelDefault(vipAddress.getSsoId());
		//设置新的默认地址
		mapper.setDefault(id);
	}
	
	@Override
	public VipAddress getDefault(Long ssoId) {
		return mapper.getDefault(ssoId);
	}
}
