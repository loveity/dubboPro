package cn.itsource.aigou.core.consts.bis;

import cn.itsource.aigou.core.consts.ConstName;
/**
 * 输入模式
 * @author nixianhua
 *
 */
public interface PropertyInputTypeConsts {
	@ConstName("文本")
	public static byte PROPERTY_INPUTTYPE_TEXT  =  0;
	@ConstName("数字")
	public static byte PROPERTY_INPUTTYPE_NUMBER = 1;
	@ConstName("日期")
	public static byte PROPERTY_INPUTTYPE_DATE = 2;
}
