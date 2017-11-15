package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class VipCart implements Serializable {
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

    /**
     * 图片
     */
    private String skuMainPic;

    /**
     * 名称
     */
    private String name;

    /**
     * 商家
     */
    private Long storeId;

    /**
     * 商家名
     */
    private String storeName;

    /**
     * 规格说明
     */
    private String skuProperties;

    /**
     * 数量
     */
    private Integer amount;

    /**
     * 是否选中
     */
    private Byte selected = 0;
    
    /**
     * 购物车sku数据
     */
    private Sku sku;
    
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

    public String getSkuMainPic() {
        return skuMainPic;
    }

    public void setSkuMainPic(String skuMainPic) {
        this.skuMainPic = skuMainPic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getSkuProperties() {
        return skuProperties;
    }

    public void setSkuProperties(String skuProperties) {
        this.skuProperties = skuProperties;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    
    public void setSelected(Byte selected) {
		this.selected = selected;
	}
    
    public Byte getSelected() {
		return selected;
	}
    
    public Sku getSku() {
		return sku;
	}
    
    public void setSku(Sku sku) {
		this.sku = sku;
	}
    
}