package cn.itsource.aigou.core.domain;

import java.io.Serializable;
import java.util.List;

/**
 * @author 
 */
public class Product implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 副名称
     */
    private String subName;

    /**
     * 商品编码
     */
    private String code;

    /**
     * 商品类型ID
     */
    private Long productType;

    /**
     * 上架时间
     */
    private Long onSaleTime;

    /**
     * 下架时间
     */
    private Long offSaleTime;

    /**
     * 状态
     */
    private Byte state;

    /**
     * 最高价
     */
    private Integer maxPrice;

    /**
     * 最低价
     */
    private Integer minPrice;

    /**
     * 销量
     */
    private Integer saleCount;

    /**
     * 浏览量
     */
    private Integer viewCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 评分
     */
    private Integer commentScore;
    
    /**
     * 品牌ID
     */
    private Long brandId;

    /**
     * 显示属性摘要
     */
    private String viewProperties;

    /**
     * 好评数
     */
    private Integer goodCommentCount;
    /**
     * 中评数
     */
    private Integer commonCommentCount;
    /**
     * 差评数
     */
    private Integer badCommentCount;
    
    /**
     * 商品扩展信息
     */
    private ProductExt productExt;
    /**
     * 商品媒体列表
     */
    private List<ProductMedia> productMediaList;

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

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getProductType() {
        return productType;
    }

    public void setProductType(Long productType) {
        this.productType = productType;
    }

    public Long getOnSaleTime() {
        return onSaleTime;
    }

    public void setOnSaleTime(Long onSaleTime) {
        this.onSaleTime = onSaleTime;
    }

    public Long getOffSaleTime() {
        return offSaleTime;
    }

    public void setOffSaleTime(Long offSaleTime) {
        this.offSaleTime = offSaleTime;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(Integer commentScore) {
        this.commentScore = commentScore;
    }

    public String getViewProperties() {
        return viewProperties;
    }

    public void setViewProperties(String viewProperties) {
        this.viewProperties = viewProperties;
    }

    public Integer getGoodCommentCount() {
        return goodCommentCount;
    }

    public void setGoodCommentCount(Integer goodCommentCount) {
        this.goodCommentCount = goodCommentCount;
    }

    public Integer getCommonCommentCount() {
        return commonCommentCount;
    }

    public void setCommonCommentCount(Integer commonCommentCount) {
        this.commonCommentCount = commonCommentCount;
    }

    public Integer getBadCommentCount() {
        return badCommentCount;
    }

    public void setBadCommentCount(Integer badCommentCount) {
        this.badCommentCount = badCommentCount;
    }

	public Long getBrandId() {
		return brandId;
	}

	public void setBrandId(Long brandId) {
		this.brandId = brandId;
	}

	public ProductExt getProductExt() {
		return productExt;
	}

	public void setProductExt(ProductExt productExt) {
		this.productExt = productExt;
	}

	public List<ProductMedia> getProductMediaList() {
		return productMediaList;
	}

	public void setProductMediaList(List<ProductMedia> productMediaList) {
		this.productMediaList = productMediaList;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", createTime=" + createTime + ", updateTime=" + updateTime + ", name=" + name
				+ ", subName=" + subName + ", code=" + code + ", productType=" + productType + ", onSaleTime="
				+ onSaleTime + ", offSaleTime=" + offSaleTime + ", state=" + state + ", maxPrice=" + maxPrice
				+ ", minPrice=" + minPrice + ", saleCount=" + saleCount + ", viewCount=" + viewCount + ", commentCount="
				+ commentCount + ", commentScore=" + commentScore + ", brandId=" + brandId + ", viewProperties="
				+ viewProperties + ", goodCommentCount=" + goodCommentCount + ", commonCommentCount="
				+ commonCommentCount + ", badCommentCount=" + badCommentCount + "]";
	}
	
	
    
}