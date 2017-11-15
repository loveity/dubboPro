package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class DictionaryValue implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 目录ID
     */
    private Long dicId;

    /**
     * 代码
     */
    private String code;

    /**
     * 字典值
     */
    private String value;

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

    public Long getDicId() {
        return dicId;
    }

    public void setDicId(Long dicId) {
        this.dicId = dicId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}