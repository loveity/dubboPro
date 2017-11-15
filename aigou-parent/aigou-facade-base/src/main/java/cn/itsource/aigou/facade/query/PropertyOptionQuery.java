package cn.itsource.aigou.facade.query;

import java.io.Serializable;

import cn.itsource.aigou.core.query.BaseQuery;

public class PropertyOptionQuery extends BaseQuery implements Serializable{

	private static final long serialVersionUID = 7150927991995185144L;
	private Long propId;
	public Long getPropId() {
		return propId;
	}
	public void setPropId(Long propId) {
		this.propId = propId;
	}
	
}
