package com.qiniu;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.Zone;
import com.qiniu.config.QiniuConfig;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;

public class QiniuUploader {
	// 创建上传对象
	public static String upload(byte[] data, String key,String bucketName) {
		try {
			//构造一个带指定Zone对象的配置类
			/*
			华东	Zone.zone0()
			华北	Zone.zone1()
			华南	Zone.zone2()
			北美	Zone.zoneNa0()
			 */
			Configuration cfg = new Configuration(Zone.zone2());
			// 调用put方法上传
			UploadManager uploadManager = new UploadManager(cfg);
			Response res = uploadManager.put(data, key, QiniuConfig.getUpToken(bucketName,key));
			//解析上传成功的结果
			DefaultPutRet putRet = JSONObject.parseObject(res.bodyString(), DefaultPutRet.class);
			return putRet.key;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}