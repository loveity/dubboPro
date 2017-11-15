package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class PayAccountFlow implements Serializable {
    private Long id;

    private Long createTime;

    /**
     * 用户ID
     */
    private Long ssoId;

    /**
     * 用户姓名
     */
    private String nickName;

    /**
     * 金额
     */
    private Integer money;

    /**
     * 类型
     */
    private Byte type;

    /**
     * 剩余可用金额
     */
    private Integer avilableBalance;

    /**
     * 剩余冻结金额
     */
    private Integer frozenBalance;

    /**
     * 业务类型
     */
    private Byte businessType;

    /**
     * 业务名
     */
    private String businessName;

    /**
     * 关联业务键
     */
    private Long businessKey;

    private Byte payChannel;

    private String payChannelName;

    /**
     * 备注
     */
    private String note;

    private String digest;

    /**
     * 统一支付单号
     */
    private String unionPaySeq;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getAvilableBalance() {
        return avilableBalance;
    }

    public void setAvilableBalance(Integer avilableBalance) {
        this.avilableBalance = avilableBalance;
    }

    public Integer getFrozenBalance() {
        return frozenBalance;
    }

    public void setFrozenBalance(Integer frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    public Byte getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Byte businessType) {
        this.businessType = businessType;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Long getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(Long businessKey) {
        this.businessKey = businessKey;
    }

    public Byte getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(Byte payChannel) {
        this.payChannel = payChannel;
    }

    public String getPayChannelName() {
        return payChannelName;
    }

    public void setPayChannelName(String payChannelName) {
        this.payChannelName = payChannelName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getUnionPaySeq() {
        return unionPaySeq;
    }

    public void setUnionPaySeq(String unionPaySeq) {
        this.unionPaySeq = unionPaySeq;
    }
}