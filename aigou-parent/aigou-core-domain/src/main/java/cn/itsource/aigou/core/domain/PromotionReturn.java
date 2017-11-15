package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class PromotionReturn implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 类型
     */
    private Byte type;

    /**
     * 满
     */
    private Integer fullAmount;

    /**
     * 减
     */
    private Integer returnValue;

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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getFullAmount() {
        return fullAmount;
    }

    public void setFullAmount(Integer fullAmount) {
        this.fullAmount = fullAmount;
    }

    public Integer getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Integer returnValue) {
        this.returnValue = returnValue;
    }
}