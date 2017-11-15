package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class SeckillSku implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    private Long seckillId;

    /**
     * 单品标题
     */
    private String skuName;

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
    private String skuPic;

    /**
     * 秒杀价
     */
    private Integer price;

    /**
     * 数量
     */
    private Integer totalCount;

    /**
     * 剩余数量
     */
    private Integer leftCount;
    
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

    public Long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Long seckillId) {
        this.seckillId = seckillId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(Integer leftCount) {
        this.leftCount = leftCount;
    }
    
    public void setSkuPic(String skuPic) {
		this.skuPic = skuPic;
	}
    
    public String getSkuPic() {
		return skuPic;
	}
}