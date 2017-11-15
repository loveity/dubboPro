package cn.itsource.aigou.core.consts;

public interface GlobalSettingNames {
	
	//系统变量设置
	//订单支付时限（小时）
	public static final String SYSTEM_PAYTIME_LIMIT_HOURS = "SYSTEM_PAYTIME_LIMIT_HOURS";
	//确认收货时限（天）
	public static final String SYSTEM_CONFIRM_SHIP_DAYS = "SYSTEM_CONFIRM_SHIP_DAYS";
	//抢购单品确认订单时限（分钟）
	public static final String SYSTEM_CONFIRM_SECKILL_MINUTES = "SYSTEM_CONFIRM_SECKILL_MINUTES";
	
	//SSO_LOGIN_URL 单点登录地址
	public static final String URL_SSO_LOGIN = "URL_SSO_LOGIN";
	public static final String URL_SSO_REG = "URL_SSO_REG";
	public static final String URL_SSO_LOGOUT = "URL_SSO_LOGOUT";
	
	//站点的地址
	public static final String URL_WWW = "URL_WWW";
	public static final String URL_PLAT = "URL_PLAT";
	public static final String URL_PAY = "URL_PAY";
	public static final String URL_PAY_DOMAIN = "URL_PAY_DOMAIN";
	public static final String URL_USERCENTER = "URL_USERCENTER";
	
	//SSO客户端统一登录地址（做web层的统一拦截）
	public static final String URL_CLIENT_LOGIN = "URL_CLIENT_LOGIN";
	public static final String URL_CLIENT_REG = "URL_CLIENT_REG";
	public static final String URL_CLIENT_LOGOUT = "URL_CLIENT_LOGOUT";
	
	//支付中心处理地址
	public static final String URL_PAY_GATEWAY = "URL_PAY_GATEWAY";
	public static final String URL_PAY_RETURN_URL = "URL_PAY_RETURN_URL";
	public static final String URL_PAY_NOTIFY_ALIPAY = "URL_PAY_NOTIFY_ALIPAY";
	
	//qiniu cdn 相关配置
	public static final String QINIU_AK = "QINIU_AK";
	public static final String QINIU_SK = "QINIU_SK";
	public static final String QINIU_BUCKET = "QINIU_BUCKET";
	public static final String QINIU_DOMAIN = "QINIU_DOMAIN";
	
	/*
	 * #WWW-WEB-STAIC-ROOT-DIR
	 * 静态化路径配置
	 * 静态化模板根目录
	 */
	public static final String WWW_WEB_STATIC_DIR = "WWW_WEB_STATIC_DIR";
	//商品分类模板路径
	public static final String WWW_WEB_STATIC_CAT_TEMPLATE = "WWW_WEB_STATIC_CAT_TEMPLATE";
	//商城首页模板路径
	public static final String WWW_WEB_STATIC_HOME_TEMPLATE = "WWW_WEB_STATIC_HOME_TEMPLATE";
	
	/*
	 * elastic-search 商品搜索ES相关配置
	 */
	public static final String ES_CLUSTER_HOST = "ES_CLUSTER_HOST";
	public static final String ES_CLUSTER_PORT = "ES_CLUSTER_PORT";
	
	/*
	 * 云通讯 短信服务
	 * cloopen sms
	 */
	public static final String CLOOPEN_ACCOUNT_SID = "CLOOPEN_ACCOUNT_SID";
	public static final String CLOOPEN_ACCOUNT_TOKEN = "CLOOPEN_ACCOUNT_TOKEN";
	public static final String CLOOPEN_APP_ID = "CLOOPEN_APP_ID";
	public static final String CLOOPEN_SMS_CODE_TEMPALTE_ID = "CLOOPEN_SMS_CODE_TEMPALTE_ID";
	
	/*
	 * Redis
	 */
	public static final String REDIS_HOST = "REDIS_HOST";
	public static final String REDIS_PORT = "REDIS_PORT";
	public static final String REDIS_AUTH = "REDIS_AUTH";
	
	/*支付宝支付配置*/
	public static final String ALIPAY_MY_RSA256_PUBKEY = "ALIPAY_MY_RSA256_PUBKEY";
	public static final String ALIPAY_MY_RSA256_PRIKEY = "ALIPAY_MY_RSA256_PRIKEY";
	public static final String ALIPAY_RSA256_PUBKEY = "ALIPAY_RSA256_PUBKEY";
	
	public static final String ALIPAY_GATEWAY = "ALIPAY_GATEWAY";
	public static final String ALIPAY_APPID = "ALIPAY_APPID";
	public static final String ALIPAY_CHARSET = "ALIPAY_CHARSET";
	public static final String ALIPAY_SIGN_TYPE = "ALIPAY_SIGN_TYPE";
	
}
