package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class PromotionScope implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 范围名
     */
    private String scopeName;

    /**
     * 类型
     */
    private Byte type;

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

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}