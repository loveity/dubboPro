package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class SkuProperty implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * SKU ID
     */
    private Long skuId;

    /**
     * 属性ID
     */
    private Long propId;

    /**
     * 属性名
     */
    private String propName;

    /**
     * 选项ID
     */
    private Long optionId;

    /**
     * 值
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

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getPropId() {
        return propId;
    }

    public void setPropId(Long propId) {
        this.propId = propId;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}