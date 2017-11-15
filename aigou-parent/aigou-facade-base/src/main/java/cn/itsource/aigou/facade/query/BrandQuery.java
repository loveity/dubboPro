package cn.itsource.aigou.facade.query;

import java.io.Serializable;

import cn.itsource.aigou.core.query.BaseQuery;

public class BrandQuery extends BaseQuery implements Serializable{
	private static final long serialVersionUID = 8007353887131487337L;
	private Long productType;
	private String keyword;
	public Long getProductType() {
		return productType;
	}
	public void setProductType(Long productType) {
		this.productType = productType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
