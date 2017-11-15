package cn.itsource.aigou.core.mapper;

import cn.itsource.aigou.core.domain.PayInteraction;

public interface PayInteractionMapper extends BaseMapper<PayInteraction> {

	/**
	 * 通过billId获取接口记录
	 * @param billId
	 * @return
	 */
	PayInteraction getByBillId(Long billId);
	
}