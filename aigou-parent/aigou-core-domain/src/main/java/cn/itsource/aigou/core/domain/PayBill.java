package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class PayBill implements Serializable {
    private Long id;

    private Long updateTime;

    private Long createTime;

    /**
     * 用户id
     */
    private Long ssoId;
    
    /**
     * 用户昵称
     */
    private String nickName;
    
    /**
     * 统一支付单号
     */
    private String unionPaySn;

    /**
     * 金额
     */
    private Integer money;

    /**
     * 业务类型
     */
    private Byte businessType;

    /**
     * 关联业务键
     */
    private Long businessKey;

    private Byte payChannel;

    /**
     * 备注
     */
    private String note;

    /**
     * 交易摘要
     */
    private String digest;

    /**
     * 支付状态
     */
    private Byte state;

    /**
     * 原支付单
     */
    private Long originalPayBillId;

    /**
     * 原统一支付单号
     */
    private String originalUnionPaySn;
    
    /**
     * 支付截止时间
     */
    private Long lastPayTime;

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

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Byte getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Byte businessType) {
        this.businessType = businessType;
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

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public Long getOriginalPayBillId() {
        return originalPayBillId;
    }

    public void setOriginalPayBillId(Long originalPayBillId) {
        this.originalPayBillId = originalPayBillId;
    }

    public String getOriginalUnionPaySn() {
        return originalUnionPaySn;
    }

    public void setOriginalUnionPaySn(String originalUnionPaySn) {
        this.originalUnionPaySn = originalUnionPaySn;
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

	public Long getLastPayTime() {
		return lastPayTime;
	}

	public void setLastPayTime(Long lastPayTime) {
		this.lastPayTime = lastPayTime;
	}
    
    
}