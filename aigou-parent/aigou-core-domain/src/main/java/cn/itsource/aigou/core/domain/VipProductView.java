package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class VipProductView implements Serializable {
    private Long id;

    private Long createTime;

    /**
     * 登录用户
     */
    private Long ssoId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * SKU ID
     */
    private Long skuId;

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

    public Long getSsoId() {
        return ssoId;
    }

    public void setSsoId(Long ssoId) {
        this.ssoId = ssoId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }
}