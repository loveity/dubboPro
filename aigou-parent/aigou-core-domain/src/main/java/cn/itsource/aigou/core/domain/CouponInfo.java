package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class CouponInfo implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 促销活动名
     */
    private String promotionName;

    /**
     * 是否开启
     */
    private Long validateTime;

    /**
     * 总量
     */
    private Integer totalCount;

    /**
     * 已发行量
     */
    private Integer publishCount;

    /**
     * 剩余量
     */
    private Integer leftCount;

    /**
     * 优惠规则
     */
    private Long promotionRuleId;

    /**
     * 对象范围
     */
    private Long scopeRuleId;

    /**
     * 说明
     */
    private String description;

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

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public Long getValidateTime() {
        return validateTime;
    }

    public void setValidateTime(Long validateTime) {
        this.validateTime = validateTime;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPublishCount() {
        return publishCount;
    }

    public void setPublishCount(Integer publishCount) {
        this.publishCount = publishCount;
    }

    public Integer getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(Integer leftCount) {
        this.leftCount = leftCount;
    }

    public Long getPromotionRuleId() {
        return promotionRuleId;
    }

    public void setPromotionRuleId(Long promotionRuleId) {
        this.promotionRuleId = promotionRuleId;
    }

    public Long getScopeRuleId() {
        return scopeRuleId;
    }

    public void setScopeRuleId(Long scopeRuleId) {
        this.scopeRuleId = scopeRuleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}