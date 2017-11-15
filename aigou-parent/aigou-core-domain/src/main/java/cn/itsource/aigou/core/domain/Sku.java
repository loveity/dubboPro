package cn.itsource.aigou.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 
 */
public class Sku implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * SKU编码
     */
    private String skuCode;
    /**
     * SKU规格名
     */
    private String skuName;

    /**
     * 市场价
     */
    private Integer marketPrice;

    /**
     * 优惠价
     */
    private Integer price;

    /**
     * 成本价
     */
    private Integer costPrice;

    /**
     * 销量
     */
    private Integer saleCount;

    /**
     * 排序
     */
    private Integer sortIndex;

    /**
     * 可用库存
     */
    private Integer availableStock;

    /**
     * 锁定库存
     */
    private Integer frozenStock;

    /**
     * SKU属性摘要
     */
    private String skuProperties;

    /**
     * 预览图
     */
    private String skuMainPic;
    
    /**
     * Sku的属性值列表
     */
    private List<SkuProperty> skuPropertyList = new ArrayList<>();

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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }
    
    public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public Integer getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Integer marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(Integer costPrice) {
        this.costPrice = costPrice;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Integer getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }

    public Integer getAvailableStock() {
        return availableStock;
    }

    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
    }

    public Integer getFrozenStock() {
        return frozenStock;
    }

    public void setFrozenStock(Integer frozenStock) {
        this.frozenStock = frozenStock;
    }

    public String getSkuProperties() {
        return skuProperties;
    }

    public void setSkuProperties(String skuProperties) {
        this.skuProperties = skuProperties;
    }

    public String getSkuMainPic() {
        return skuMainPic;
    }

    public void setSkuMainPic(String skuMainPic) {
        this.skuMainPic = skuMainPic;
    }

	public List<SkuProperty> getSkuPropertyList() {
		return skuPropertyList;
	}

	public void setSkuPropertyList(List<SkuProperty> skuPropertyList) {
		this.skuPropertyList = skuPropertyList;
	}
}