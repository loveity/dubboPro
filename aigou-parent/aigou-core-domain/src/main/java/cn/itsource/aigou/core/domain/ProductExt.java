package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class ProductExt implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 简介
     */
    private String description;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 图文内容
     */
    private String richContent;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getRichContent() {
        return richContent;
    }

    public void setRichContent(String richContent) {
        this.richContent = richContent;
    }
}