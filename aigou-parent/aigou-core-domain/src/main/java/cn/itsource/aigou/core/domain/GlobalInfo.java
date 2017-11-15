package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class GlobalInfo implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 代码
     */
    private String code;

    /**
     * 分组
     */
    private String groupName;

    /**
     * 值
     */
    private String val;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}