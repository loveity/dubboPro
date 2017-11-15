package cn.itsource.aigou.facade;

import java.util.List;
import java.util.Map;

import cn.itsource.aigou.core.utils.Page;
import cn.itsource.aigou.core.utils.Ret;
import cn.itsource.aigou.facade.query.ProductQuery;

public interface CommonService {
	public static final int SMS_CODE_VALID_SECONDS = 300;
	/**
	 * 发送短信验证码服务
	 * @param phone
	 * @return
	 */
	Ret sendSmsCode(String phone);
	
	/**
	 * 验证手机短信验证码是否正确
	 * 注意该方法，如果短信验证码正确，将失效（即只能使用一次）
	 * @param phone
	 * @param code
	 * @return
	 */
	Ret validateSmsCode(String phone,String code);
	
	/**
	 * 获取七牛云上传的TOKEN
	 * @param bucketName 空间名
	 * @return
	 */
	String getQiniuUpToken(String bucketName);
	
	/**
	 * 上传数据到七牛CDN
	 * @param data 数据
	 * @param key 文件名
	 * @param bucketName 空间名
	 * @return
	 */
	String uploadToQiniuCdn(byte[] data,String key,String bucketName);
	
	/**
	 * 新增/更新商品文档到ES
	 * @param id 商品id
	 * @param json 商品josn格式数据
	 */
	void saveOrUpdateProduct2Es(String id, String json);
	
	/**
	 * 批量添加/更新商品到es
	 * @param dataList
	 */
	void saveOrUpdateProduct2Es(List<Map<String, Object>> dataList);
	
	/**
	 * 删除ES商品文档
	 * @param id 文档ID
	 */
	void deleteEsProduct(String id);
	
	/**
	 * 批量删除ES商品文档
	 * @param ids
	 */
	long deleteEsProducts(Long... ids);
	/**
	 * 通过ID获取ES商品文档
	 * @param id 文档ID
	 * @return
	 */
	Map<String, Object> getEsProduct(String id);
	
	/**
	 * 批量获取ES商品文档
	 * @param ids 文档ID数组
	 * @return
	 */
	List<Map<String, Object>> getEsProducts(String... ids);
	
	/**
	 * 通过dsl模板和参数获取ES商品文档
	 * 
	 * @param dslTemplate dsl模板
	 * @param params   参数 
	 * 如：
	 *  dslTtemplate ： {"query":{"match":{"id":"{{id}}"}}}
	 *  params(map) : new HashMap().put("id",1);
	 * @return
	 */
	List<Map<String, Object>> queryEsProducts(String dslTemplate, Map<String, Object> params);
	
	/**
	 * 通过product查询对象对product的es数据进行查询分页
	 * @param query
	 * @return
	 */
	Page<Map<String, Object>> queryEsProducts(ProductQuery query);

	/**
	 * 
	 * @param ssoId 用户ID
	 * @return
	 */
	String getSsoTGC(String ssoId);
	
	/**
	 * 刷新tgc的过期时间
	 * @param tgc 
	 */
	void refreshTGCExpires(String tgc);
	
	/**
	 * 验证tgc是否有效
	 * @param tgc 
	 * @return
	 */
	boolean validateSsoTGC(String tgc);
	
	/**
	 * 生成访问票据ST
	 * @param tgc
	 * @return 
	 */
	String getSsoST(String tgc);
	
	/**
	 * 验证票据是否有效
	 * @param st 票据
	 * @return
	 */
	String validateSsoST(String st);

	/**
	 * 删除tgc
	 * @param tgc
	 */
	void deleteSsoTGC(String tgc);

	/**
	 * 设置Redis中ssoId
	 * @param ssoId
	 * @return
	 */
	void setRedisSsoId(Long ssoId);
	
	/**
	 * 刷新Redis中ssoId的过期时间
	 * @param ssoId
	 * @return
	 */
	Long refreshRedisSsoId(Long ssoId);
	
	/**
	 * 获取Redis中ssoId
	 * @param ssoId
	 * @return
	 */
	String getRedisSsoId(Long ssoId);
	
	/**
	 * 删除Redis中ssoId
	 * @param ssoId
	 * @return
	 */
	Long delRedisSsoId(Long ssoId);

	/**
	 * 设置redis数据
	 * @param key
	 * @param expireSeconds
	 * @param value
	 */
	void redisSetex(String key, int expireSeconds, String value);
	
	/**
	 * 获取redis key的值
	 * @param key
	 * @return
	 */
	String redisGet(String key);
	/**
	 * 删除redis key的值
	 * @param key
	 * @return
	 */
	Long redisDel(String key);
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	Long redisDecr(String key);
}
