package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author
 */
public class SeckillResult implements Serializable {
	private Long id;

	private Long createTime;

	private Long seckillSkuId;

	private Long ssoId;

	/**
	 * 数量
	 */
	private Integer totalCount;
	/**
	 * 确认订单的最终时间
	 */
	private Long lastConfirmTime;
	/**
	 * 状态
	 */
	private Byte state;

	private static final long serialVersionUID = 1L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getSeckillSkuId() {
		return seckillSkuId;
	}

	public void setSeckillSkuId(Long seckillSkuId) {
		this.seckillSkuId = seckillSkuId;
	}

	public Long getSsoId() {
		return ssoId;
	}

	public void setSsoId(Long ssoId) {
		this.ssoId = ssoId;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Long getLastConfirmTime() {
		return lastConfirmTime;
	}

	public void setLastConfirmTime(Long lastConfirmTime) {
		this.lastConfirmTime = lastConfirmTime;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

}