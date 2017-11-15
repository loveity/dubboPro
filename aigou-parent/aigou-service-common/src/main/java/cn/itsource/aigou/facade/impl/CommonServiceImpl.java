package cn.itsource.aigou.facade.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.qiniu.QiniuUploader;
import com.qiniu.config.QiniuConfig;
import com.sun.media.jfxmedia.control.VideoDataBuffer;

import cn.itsource.aigou.core.consts.msg.UserCenterMsgConsts;
import cn.itsource.aigou.core.utils.Page;
import cn.itsource.aigou.core.utils.Ret;
import cn.itsource.aigou.core.utils.StrUtils;
import cn.itsource.aigou.core.utils.encrypt.MD5;
import cn.itsource.aigou.es.ProductSearchHelper;
import cn.itsource.aigou.facade.CommonService;
import cn.itsource.aigou.facade.query.ProductQuery;
import cn.itsource.aigou.jedis.RedisUtils;

@Service
public class CommonServiceImpl implements CommonService {

	@Override
	public Ret sendSmsCode(String phone) {
		String key = "SMSCODE:" + phone;
		//如果短信验证码还未过期，则使用已存在的验证码，如果已经过期，则使用新的随机验证码
		String randomCode = StrUtils.getRandomString(6);
		//如果验证码已经存在，并且发送间隔小于60秒，则禁止发送
		String codeInfo = RedisUtils.get(key);
		if(StringUtils.isNotBlank(codeInfo)){
			String[] data = codeInfo.split(":");
			long sendMills = Long.parseLong(data[1]);
			if(System.currentTimeMillis()-sendMills < 60 * 1000){//发送间隔小于60秒
				Ret.me().setCode(UserCenterMsgConsts.SMSCODE_ERR_FREQUNCE);
			}
			//使用已存在的验证码
			randomCode = data[0];
		}
		
		String[] params = new String[2];
		params[0] = randomCode;
		params[1] = "" + (int)(CommonService.SMS_CODE_VALID_SECONDS/60);
		//CloopenHelper.sendSms(phone, GlobalSetting.get(GlobalSettingNames.CLOOPEN_SMS_CODE_TEMPALTE_ID), params);
		System.out.println("SendSmsCode = " + randomCode);

		//设置/刷新 短信验证码发送信息到Redis
		RedisUtils.setex(key, CommonService.SMS_CODE_VALID_SECONDS, randomCode+":"+System.currentTimeMillis());
		
		return Ret.me();
	}
	
	@Override
	public Ret validateSmsCode(String phone, String code) {
		String key = "SMSCODE:" + phone;
		String codeInfo = RedisUtils.get(key);
		if(StringUtils.isBlank(codeInfo)){//不存在或已过期
			return Ret.me().setSuccess(false).setCode(UserCenterMsgConsts.SMSCODE_EXPIRED);
		}
		String[] data = codeInfo.split(":");
		if(!data[0].equals(code)){//验证码不正确
			return Ret.me().setSuccess(false).setCode(UserCenterMsgConsts.SMS_CODE_INVALIDE);
		}
		//验证码正确，移除该key
		RedisUtils.del(key);
		return Ret.me();
	}

	@Override
	public String getQiniuUpToken(String bucketName) {
		return QiniuConfig.getUpToken(bucketName);
	}

	@Override
	public String uploadToQiniuCdn(byte[] data, String key, String bucketName) {
		return QiniuUploader.upload(data, key, bucketName);
	}

	@Override
	public void saveOrUpdateProduct2Es(String id, String json) {
		ProductSearchHelper.saveOrUpdate(id, json);
	}

	@Override
	public void saveOrUpdateProduct2Es(List<Map<String, Object>> dataList) {
		ProductSearchHelper.saveOrUpdate(dataList);
	}

	@Override
	public void deleteEsProduct(String id) {
		ProductSearchHelper.delete(id);
	}

	@Override
	public Map<String, Object> getEsProduct(String id) {
		return ProductSearchHelper.get(id);
	}

	@Override
	public List<Map<String, Object>> getEsProducts(String... ids) {
		return ProductSearchHelper.get(ids);
	}

	@Override
	public List<Map<String, Object>> queryEsProducts(String dslTemplate, Map<String, Object> params) {
		return ProductSearchHelper.query(dslTemplate, params);
	}

	@Override
	public Page<Map<String, Object>> queryEsProducts(ProductQuery query) {
		return ProductSearchHelper.query(query);
	}

	@Override
	public long deleteEsProducts(Long... ids) {
		return ProductSearchHelper.delete(ids);
	}

	@Override
	public String getSsoTGC(String ssoId) {
		int expireSeconds = 30 * 60;
		String tgc = MD5.getMD5(ssoId+StrUtils.getComplexRandomString(32));
		String tgt = ssoId;
		String tgcKey = "TGC-"+tgc;
		RedisUtils.setex(tgcKey, expireSeconds, tgt);
		return tgc;
	}
	
	@Override
	public void refreshTGCExpires(String tgc) {
		int expireSeconds = 30 * 60;
		String tgcKey = "TGC-"+tgc;
		RedisUtils.expire(tgcKey, expireSeconds);
	}
	
	@Override
	public void deleteSsoTGC(String tgc) {
		String tgcKey = "TGC-"+tgc;
		String tgt = RedisUtils.get(tgcKey);
		RedisUtils.del(tgcKey);
		//同时删除SsoId
		String ssoIdKey = "u-"+tgt;
		RedisUtils.del(ssoIdKey);
	}

	@Override
	public boolean validateSsoTGC(String tgc) {
		String tgcKey = "TGC-"+tgc;
		String cacheTgc = RedisUtils.get(tgcKey);
		return null!=cacheTgc;
	}

	@Override
	public String getSsoST(String tgc) {
		int expireSeconds = 30;
		String tgcKey = "TGC-"+tgc;
		String tgt = RedisUtils.get(tgcKey);
		String st = MD5.getMD5(tgt+StrUtils.getComplexRandomString(32));
		String stKey = "ST-"+st;
		RedisUtils.setex(stKey, expireSeconds, tgt);
		return st;
	}

	@Override
	public String validateSsoST(String st) {
		String stKey = "ST-"+st;
		String tgt = RedisUtils.get(stKey);
		RedisUtils.del(stKey);
		return tgt;
	}
	
	@Override
	public void setRedisSsoId(Long ssoId) {
		int expireSeconds = 30 * 60;
		String key = "u-"+ssoId;
		RedisUtils.setex(key, expireSeconds, ssoId.toString());
	}
	
	@Override
	public Long refreshRedisSsoId(Long ssoId) {
		int expireSeconds = 30 * 60;
		String key = "u-"+ssoId;
		Long ret = RedisUtils.expire(key, expireSeconds);
		return ret;
	}
	
	@Override
	public String getRedisSsoId(Long ssoId) {
		String key = "u-"+ssoId;
		return RedisUtils.get(key);
	}
	
	@Override
	public Long delRedisSsoId(Long ssoId) {
		String key = "u-"+ssoId;
		return RedisUtils.del(key);
	}

	@Override
	public void redisSetex(String key, int expireSeconds, String value) {
		RedisUtils.setex(key, expireSeconds, value);
	}
	
	@Override
	public String redisGet(String key){
		return RedisUtils.get(key);
	}
	
	@Override
	public Long redisDel(String key){
		return RedisUtils.del(key);
	}
	
	@Override
	public Long redisDecr(String key) {
		return RedisUtils.decr(key);
	}
	
}
