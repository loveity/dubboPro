package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class CmsType implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 类型名
     */
    private String name;

    /**
     * 父类
     */
    private Long pId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }
}