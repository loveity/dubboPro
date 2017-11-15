package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class Coupon implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 券ID
     */
    private Long couponId;

    /**
     * 过期时间
     */
    private Long validateTime;

    /**
     * 使用范围
     */
    private Long scopeRules;

    /**
     * 优惠说明
     */
    private Long promotionRules;

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

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getValidateTime() {
        return validateTime;
    }

    public void setValidateTime(Long validateTime) {
        this.validateTime = validateTime;
    }

    public Long getScopeRules() {
        return scopeRules;
    }

    public void setScopeRules(Long scopeRules) {
        this.scopeRules = scopeRules;
    }

    public Long getPromotionRules() {
        return promotionRules;
    }

    public void setPromotionRules(Long promotionRules) {
        this.promotionRules = promotionRules;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }
}