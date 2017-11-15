package com.qiniu.config;

import com.qiniu.util.Auth;

import cn.itsource.aigou.core.consts.GlobalSettingNames;
import cn.itsource.aigou.core.utils.GlobalSetting;

public class QiniuConfig {
	
	public static Auth auth;

	public static String getUpToken(String bucketName,String key){
		 //密钥配置
		if(null==auth){
			auth = Auth.create(AK(), SK());
		}
		if(null==bucketName) bucketName = BUCKET();
		return auth.uploadToken(bucketName,key);
	}
	public static String getUpToken(String bucketName){
		//密钥配置
		if(null==auth){
			auth = Auth.create(AK(), SK());
		}
		if(null==bucketName) bucketName = BUCKET();
		return auth.uploadToken(bucketName);
	}
	
	public static String AK(){
		return GlobalSetting.get(GlobalSettingNames.QINIU_AK);
	}
	public static String SK(){
		return GlobalSetting.get(GlobalSettingNames.QINIU_SK);
	}
	public static String BUCKET(){
		return GlobalSetting.get(GlobalSettingNames.QINIU_BUCKET);
	}
	public static String DOMAIN(){
		return GlobalSetting.get(GlobalSettingNames.QINIU_DOMAIN);
	}
}
