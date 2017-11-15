package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class OrderReturn implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 原订单ID
     */
    private Long orderId;

    /**
     * 原订单号
     */
    private String orderSn;

    /**
     * 退款单号
     */
    private String returnSn;

    /**
     * 申请时间
     */
    private Long applyTime;

    /**
     * 订单总价
     */
    private Integer totalMoney;

    /**
     * 应扣运费
     */
    private Integer carriageFee;

    /**
     * 应退金额
     */
    private Integer realMoney;

    /**
     * 已退金额
     */
    private Integer returnPayMoney;

    /**
     * 用户
     */
    private Long ssoId;

    /**
     * 退货/取消原因
     */
    private String reason;

    /**
     * 退款渠道
     */
    private Byte payChannel;

    /**
     * 状态
     */
    private Byte state;

    /**
     * 备注
     */
    private String remark;

    /**
     * 快递服务商
     */
    private Long returnShipStore;

    /**
     * 快递单号
     */
    private String returnShipSn;

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

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getReturnSn() {
        return returnSn;
    }

    public void setReturnSn(String returnSn) {
        this.returnSn = returnSn;
    }

    public Long getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Long applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Integer totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getCarriageFee() {
        return carriageFee;
    }

    public void setCarriageFee(Integer carriageFee) {
        this.carriageFee = carriageFee;
    }

    public Integer getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(Integer realMoney) {
        this.realMoney = realMoney;
    }

    public Integer getReturnPayMoney() {
        return returnPayMoney;
    }

    public void setReturnPayMoney(Integer returnPayMoney) {
        this.returnPayMoney = returnPayMoney;
    }

    public Long getSsoId() {
        return ssoId;
    }

    public void setSsoId(Long ssoId) {
        this.ssoId = ssoId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Byte getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Byte payChannel) {
        this.payChannel = payChannel;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getReturnShipStore() {
        return returnShipStore;
    }

    public void setReturnShipStore(Long returnShipStore) {
        this.returnShipStore = returnShipStore;
    }

    public String getReturnShipSn() {
        return returnShipSn;
    }

    public void setReturnShipSn(String returnShipSn) {
        this.returnShipSn = returnShipSn;
    }
}