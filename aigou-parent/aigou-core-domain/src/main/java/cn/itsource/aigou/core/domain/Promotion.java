package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class Promotion implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 促销活动名
     */
    private String promotionName;

    /**
     * 促销图标
     */
    private String icon;

    /**
     * 是否开启
     */
    private Byte isOpen;

    /**
     * 对象范围
     */
    private Long scopeRuleId;

    /**
     * 优惠规则
     */
    private Long promotionRuleId;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Byte getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Byte isOpen) {
        this.isOpen = isOpen;
    }

    public Long getScopeRuleId() {
        return scopeRuleId;
    }

    public void setScopeRuleId(Long scopeRuleId) {
        this.scopeRuleId = scopeRuleId;
    }

    public Long getPromotionRuleId() {
        return promotionRuleId;
    }

    public void setPromotionRuleId(Long promotionRuleId) {
        this.promotionRuleId = promotionRuleId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}