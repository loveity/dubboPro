package cn.itsource.aigou.service;

import cn.itsource.aigou.core.common.base.IBaseService;
import cn.itsource.aigou.core.domain.PayInteraction;

public interface IPayInteractionService extends IBaseService<PayInteraction> {

	/**
	 * 通过billId获取接口记录
	 * @param billId
	 * @return
	 */
	PayInteraction getByBillId(Long billId);
	
}
