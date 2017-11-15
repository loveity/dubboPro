package cn.itsource.aigou.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 
 */
public class Order implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 登录账号
     */
    private Long ssoId;

    /**
     * 订单号
     */
    private String orderSn;

    /**
     * 商户
     */
    private Long storeId;

    /**
     * 商户名
     */
    private String storeName;

    /**
     * 留言
     */
    private String leaveword;

    /**
     * 状态
     */
    private Byte state;

    /**
     * 运费
     */
    private Integer carriageFee;

    /**
     * 管理员备注
     */
    private String remark;

    /**
     * 订单总价
     */
    private Integer totalMoney;

    /**
     * 优惠总额
     */
    private Integer discountMoney;

    /**
     * 实际金额
     */
    private Integer realMoney;

    /**
     * 实付金额
     */
    private Integer payMoney;

    /**
     * 支付方式
     */
    private Byte payChannel;

    /**
     * 支付时间
     */
    private Long payTime;

    /**
     * 优惠券ID
     */
    private Long couponId;

    /**
     * 优惠券金额
     */
    private Integer couponMoney;

    /**
     * 促销
     */
    private Long promotionId;

    /**
     * 促销金额
     */
    private Integer promotionMoney;

    /**
     * 订单来源
     */
    private Byte orderFrom;

    /**
     * 交易完成时间
     */
    private Long finishedTime;

    /**
     * 评价状态
     */
    private Byte commentStatus;

    /**
     * 评价时间
     */
    private Long commentTime;

    /**
     * 快递服务商
     */
    private Long shipStore;

    /**
     * 快递单号
     */
    private String shipSn;

    /**
     * 配送时间
     */
    private String shipTime;
    
    /**
     * 发货时间
     */

    private Long shipSendTime;
    
    /**
     * 订单摘要
     */
    private String digest;

    /**
     * 退换货状态
     */
    private Byte saleReturnState;

    /**
     * 退货单号
     */
    private String returnOrderSn;

    /**
     * 换货单号
     */
    private String changeOrderSn;

    /**
     * 是否需要发票
     */
    private Byte needBill;
    
    /**
     * 确认收货截止时间
     */
    private Long lastConfirmShipTime;
    /**
     * 支付截止时间
     */
    private Long lastPayTime;
    /**
     * 订单明细
     */
    private List<OrderDetail> detailList;
    /**
     * 订单地址
     */
    private OrderAddress orderAddress ;
    

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

    public Long getSsoId() {
        return ssoId;
    }

    public void setSsoId(Long ssoId) {
        this.ssoId = ssoId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
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

    public String getLeaveword() {
        return leaveword;
    }

    public void setLeaveword(String leaveword) {
        this.leaveword = leaveword;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Integer getCarriageFee() {
        return carriageFee;
    }

    public void setCarriageFee(Integer carriageFee) {
        this.carriageFee = carriageFee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Integer totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Integer discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Integer getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Integer realMoney) {
        this.realMoney = realMoney;
    }

    public Integer getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Integer payMoney) {
        this.payMoney = payMoney;
    }

    public Byte getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Byte payChannel) {
        this.payChannel = payChannel;
    }

    public Long getPayTime() {
        return payTime;
    }

    public void setPayTime(Long payTime) {
        this.payTime = payTime;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Integer getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(Integer couponMoney) {
        this.couponMoney = couponMoney;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Integer getPromotionMoney() {
        return promotionMoney;
    }

    public void setPromotionMoney(Integer promotionMoney) {
        this.promotionMoney = promotionMoney;
    }

    public Byte getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(Byte orderFrom) {
        this.orderFrom = orderFrom;
    }

    public Long getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Long finishedTime) {
        this.finishedTime = finishedTime;
    }

    public Byte getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(Byte commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Long getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Long commentTime) {
        this.commentTime = commentTime;
    }

    public Long getShipStore() {
        return shipStore;
    }

    public void setShipStore(Long shipStore) {
        this.shipStore = shipStore;
    }

    public String getShipSn() {
        return shipSn;
    }

    public void setShipSn(String shipSn) {
        this.shipSn = shipSn;
    }

    public String getShipTime() {
        return shipTime;
    }

    public void setShipTime(String shipTime) {
        this.shipTime = shipTime;
    }
    
    public void setShipSendTime(Long shipSendTime) {
		this.shipSendTime = shipSendTime;
	}
    
    public Long getShipSendTime() {
		return shipSendTime;
	}

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Byte getSaleReturnState() {
        return saleReturnState;
    }

    public void setSaleReturnState(Byte saleReturnState) {
        this.saleReturnState = saleReturnState;
    }

    public String getReturnOrderSn() {
        return returnOrderSn;
    }

    public void setReturnOrderSn(String returnOrderSn) {
        this.returnOrderSn = returnOrderSn;
    }

    public String getChangeOrderSn() {
        return changeOrderSn;
    }

    public void setChangeOrderSn(String changeOrderSn) {
        this.changeOrderSn = changeOrderSn;
    }

    public Byte getNeedBill() {
        return needBill;
    }

    public void setNeedBill(Byte needBill) {
        this.needBill = needBill;
    }

	public List<OrderDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<OrderDetail> detailList) {
		this.detailList = detailList;
	}

	public OrderAddress getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(OrderAddress orderAddress) {
		this.orderAddress = orderAddress;
	}

	public Long getLastConfirmShipTime() {
		return lastConfirmShipTime;
	}

	public void setLastConfirmShipTime(Long lastConfirmShipTime) {
		this.lastConfirmShipTime = lastConfirmShipTime;
	}

	public Long getLastPayTime() {
		return lastPayTime;
	}

	public void setLastPayTime(Long lastPayTime) {
		this.lastPayTime = lastPayTime;
	}
	
	
    
}