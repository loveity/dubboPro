package cn.itsource.aigou.facade.query;

import java.io.Serializable;

import cn.itsource.aigou.core.query.BaseQuery;

public class PropertyQuery extends BaseQuery implements Serializable{

	private static final long serialVersionUID = -4061544269522321632L;
	
	private Long productType;

	public Long getProductType() {
		return productType;
	}

	public void setProductType(Long productType) {
		this.productType = productType;
	}
	
}
