package com.cloopen.rest;

import java.util.HashMap;
import java.util.Set;

import com.cloopen.rest.sdk.CCPRestSDK;

import cn.itsource.aigou.core.consts.GlobalSettingNames;
import cn.itsource.aigou.core.utils.GlobalSetting;

public class CloopenHelper {
	
	public static void sendSms(String phones,String templateId,String...params){
		
		HashMap<String, Object> result = null;

		CCPRestSDK restAPI = new CCPRestSDK();
		restAPI.init("app.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		restAPI.setAccount(GlobalSetting.get(GlobalSettingNames.CLOOPEN_ACCOUNT_SID),
				GlobalSetting.get(GlobalSettingNames.CLOOPEN_ACCOUNT_TOKEN));// 初始化主帐号和主帐号TOKEN
		restAPI.setAppId(GlobalSetting.get(GlobalSettingNames.CLOOPEN_APP_ID));// 初始化应用ID
		result = restAPI.sendTemplateSMS(phones,templateId ,params);
		System.out.println("SDKTestSendTemplateSMS result=" + result);
		
		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）
			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for(String key:keySet){
				Object object = data.get(key);
				System.out.println(key +" = "+object);
			}
		}else{
			//异常返回输出错误码和错误信息
			System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
		}
	}
}
