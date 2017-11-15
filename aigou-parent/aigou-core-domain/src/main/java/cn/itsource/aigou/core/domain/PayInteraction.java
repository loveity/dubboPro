package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class PayInteraction implements Serializable {
    private Long id;

    private Long updateTime;

    private Long createTime;

    /**
     * 统一支付单号
     */
    private String unionPaySn;

    /**
     * 支付单ID
     */
    private Long payBillId;

    /**
     * 支付渠道
     */
    private Byte payChannel;

    /**
     * 交易金额
     */
    private Integer money;

    /**
     * 三方流水号
     */
    private String returnSeq;

    /**
     * 三方返回金额
     */
    private Integer returnMoney;

    /**
     * 通知时间
     */
    private Long notifyTime;

    /**
     * 状态
     */
    private Byte state;

    /**
     * 数据包
     */
    private String reactionData;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getUnionPaySn() {
        return unionPaySn;
    }

    public void setUnionPaySn(String unionPaySn) {
        this.unionPaySn = unionPaySn;
    }

    public Long getPayBillId() {
        return payBillId;
    }

    public void setPayBillId(Long payBillId) {
        this.payBillId = payBillId;
    }

    public Byte getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Byte payChannel) {
        this.payChannel = payChannel;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getReturnSeq() {
        return returnSeq;
    }

    public void setReturnSeq(String returnSeq) {
        this.returnSeq = returnSeq;
    }

    public Integer getReturnMoney() {
        return returnMoney;
    }

    public void setReturnMoney(Integer returnMoney) {
        this.returnMoney = returnMoney;
    }

    public Long getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(Long notifyTime) {
        this.notifyTime = notifyTime;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getReactionData() {
        return reactionData;
    }

    public void setReactionData(String reactionData) {
        this.reactionData = reactionData;
    }
}