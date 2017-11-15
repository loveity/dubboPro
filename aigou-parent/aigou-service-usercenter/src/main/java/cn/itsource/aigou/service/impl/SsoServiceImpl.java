package cn.itsource.aigou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.consts.bis.RegChannelConsts;
import cn.itsource.aigou.core.consts.msg.UserCenterMsgConsts;
import cn.itsource.aigou.core.domain.Sso;
import cn.itsource.aigou.core.mapper.SsoMapper;
import cn.itsource.aigou.core.utils.BitStatesUtils;
import cn.itsource.aigou.core.utils.Ret;
import cn.itsource.aigou.core.utils.StrUtils;
import cn.itsource.aigou.core.utils.encrypt.MD5;
import cn.itsource.aigou.facade.CommonService;
import cn.itsource.aigou.facade.PayCenterService;
import cn.itsource.aigou.service.ISsoService;
import cn.itsource.aigou.service.IVipBaseService;

@Service
public class SsoServiceImpl extends BaseServiceImpl<Sso> implements ISsoService {
	@Autowired
	private SsoMapper mapper;

	@Autowired
	private IVipBaseService vipBaseService;
	
	@Reference
	private CommonService commonService;
	
	@Reference
	private PayCenterService payCenterService;
	
	@Override
	protected BaseMapper<Sso> getMapper() {
		return mapper;
	}

	@Override
	public Sso getSsoByPhone(String phone) {
		Sso sso = mapper.getSsoByPhone(phone);
		return sso;
	}

	@Override
	public Ret regUserByPhone(String phone, String password, String smsCaptcha) {
		// 参数验证（业务类）
		// 是否已存在
		Sso existSso = this.getSsoByPhone(phone);
		if (null != existSso) {
			return Ret.me().setSuccess(false).setCode(UserCenterMsgConsts.PHONE_NUMBER_EXISTS);
		}
		// 验证码是否可用
		Ret smsCodeValidateRet = commonService.validateSmsCode(phone, smsCaptcha);
		if (!smsCodeValidateRet.isSuccess()) {
			return smsCodeValidateRet;
		}
		// 开始注册业务
		Sso sso = new Sso();
		//获取盐值
		String salt = StrUtils.getComplexRandomString(32);
		//通过盐值进行MD5散列
		String md5Password = MD5.getMD5(password+salt);
		sso.setCreateTime(System.currentTimeMillis());
		sso.setUpdateTime(System.currentTimeMillis());
		sso.setNickName(phone);
		sso.setSalt(salt);
		sso.setPassword(md5Password);
		sso.setPhone(phone);
		//状态设置（手机注册，默认即:已经激活，手机已经认证）
		long state = BitStatesUtils.OP_REGISTED;
		state = BitStatesUtils.addState(state, BitStatesUtils.OP_ACTIVED);
		state = BitStatesUtils.addState(state, BitStatesUtils.OP_AUTHED_PHONE);
		sso.setBitState(state);
		//存储到数据库
		mapper.savePart(sso);
		//同步创建用户基本资料记录
		vipBaseService.create(sso,RegChannelConsts.PHONE);
		//同步创建用户账户信息
		payCenterService.createVipAccount(sso);
		return Ret.me();
	}

	@Override
	public Ret login(String username, String password) {
		//通过手机号登录
		Sso sso = this.getSsoByPhone(username);
		//通过邮箱登录
		//if(sso==null) sso = this.getSsoByEmail(username);
		//不存在的用户
		if(null==sso){
			return Ret.me().setSuccess(false).setCode(UserCenterMsgConsts.INVALIDE_USER_PASSWORD);
		}
		//密码不正确
		if(!sso.getPassword().equals(MD5.getMD5(password+sso.getSalt()))){
			return Ret.me().setSuccess(false).setCode(UserCenterMsgConsts.INVALIDE_USER_PASSWORD);
		}
		
		return Ret.me().setData(sso.getId());
	}
}
