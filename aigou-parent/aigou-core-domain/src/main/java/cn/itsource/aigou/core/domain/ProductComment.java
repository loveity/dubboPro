package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class ProductComment implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 订单
     */
    private Long orderId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 得分
     */
    private Integer score;

    /**
     * 评论
     */
    private String comment;

    /**
     * 等级
     */
    private Byte level;

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }
}