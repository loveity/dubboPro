package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class Property implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 属性名
     */
    private String propName;

    /**
     * 商品分类
     */
    private Long productType;

    /**
     * 属性类型
     */
    private Byte type;

    /**
     * 输入模式
     */
    private Byte inputMode;

    /**
     * 输入类型
     */
    private Byte inputType;

    /**
     * 验证规则
     */
    private String validatePattern;

	private String remark;

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

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getInputMode() {
        return inputMode;
    }

    public void setInputMode(Byte inputMode) {
        this.inputMode = inputMode;
    }

    public Byte getInputType() {
        return inputType;
    }

    public void setInputType(Byte inputType) {
        this.inputType = inputType;
    }

    public String getValidatePattern() {
        return validatePattern;
    }

    public void setValidatePattern(String validatePattern) {
        this.validatePattern = validatePattern;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	@Override
	public String toString() {
		return "Property [id=" + id + ", createTime=" + createTime + ", updateTime=" + updateTime + ", propName="
				+ propName + ", productType=" + productType + ", type=" + type + ", inputMode=" + inputMode
				+ ", inputType=" + inputType + ", validatePattern=" + validatePattern + ", remark=" + remark + "]";
	}
    
    
}