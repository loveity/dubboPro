package cn.itsource.aigou.service;

import java.util.List;

import cn.itsource.aigou.core.common.base.IBaseService;
import cn.itsource.aigou.core.domain.PropertyOption;
import cn.itsource.aigou.core.query.BaseQuery;

public interface IPropertyOptionService extends IBaseService<PropertyOption> {

	List<PropertyOption> getPropertieOptions(BaseQuery qObject);
	
}
