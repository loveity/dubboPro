package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class PropertyOption implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 属性ID
     */
    private Long propId;

    /**
     * 选项值
     */
    private String optionValue;

    /**
     * 配图
     */
    private String optionPic;

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

    public Long getPropId() {
        return propId;
    }

    public void setPropId(Long propId) {
        this.propId = propId;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    public String getOptionPic() {
        return optionPic;
    }

    public void setOptionPic(String optionPic) {
        this.optionPic = optionPic;
    }
}