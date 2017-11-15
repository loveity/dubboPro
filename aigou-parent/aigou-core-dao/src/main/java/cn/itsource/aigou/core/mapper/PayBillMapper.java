package cn.itsource.aigou.core.mapper;

import org.apache.ibatis.annotations.Param;

import cn.itsource.aigou.core.domain.PayBill;

public interface PayBillMapper extends BaseMapper<PayBill> {

	/**
	 * 通过业务类型和业务ID查询支付单
	 * @param bisType
	 * @param bisId
	 * @return
	 */
	PayBill queryBill(@Param("bisType") byte bisType, @Param("bisId") Long bisId);

	/**
	 * 获取指定支付单号的支付单
	 * @param paySn
	 * @return
	 */
	PayBill getPayBill(String paySn);

	
}