package cn.itsource.aigou.core.consts.bis;

import cn.itsource.aigou.core.consts.ConstName;
/**
 * 属性类型
 * @author nixianhua
 *
 */
public interface ProductStateConsts {
	@ConstName("下架")
	public static byte PRODUCT_STATE_OFFSALE  =  0;
	@ConstName("上架")
	public static byte PRODUCT_STATE_ONSALE = 1;
}
