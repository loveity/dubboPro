package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class PayCredit implements Serializable {
    private Long id;

    private Long createTime;

    /**
     * 用户
     */
    private Long ssoId;

    /**
     * 业务类型
     */
    private Byte businessType;

    /**
     * 业务名
     */
    private String businessName;

    /**
     * 业务键
     */
    private Long businessKey;

    /**
     * 业务说明
     */
    private String businessNote;

    /**
     * 积分
     */
    private Integer credit;

    /**
     * 交易类型
     */
    private Byte type;

    /**
     * 积分余额
     */
    private Integer creditBalance;

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

    public String getBusinessNote() {
        return businessNote;
    }

    public void setBusinessNote(String businessNote) {
        this.businessNote = businessNote;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(Integer creditBalance) {
        this.creditBalance = creditBalance;
    }
}