package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class PayAccount implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 用户ID
     */
    private Long ssoId;

    /**
     * 可用金额
     */
    private Integer useableBalance;

    /**
     * 冻结金额
     */
    private Integer frozenBalance;

    /**
     * 积分
     */
    private Integer creditBanance;

    /**
     * 支付密码
     */
    private String payPassword;

    /**
     * 优惠券数
     */
    private Integer couponCount;

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

    public Integer getUseableBalance() {
        return useableBalance;
    }

    public void setUseableBalance(Integer useableBalance) {
        this.useableBalance = useableBalance;
    }

    public Integer getFrozenBalance() {
        return frozenBalance;
    }

    public void setFrozenBalance(Integer frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    public Integer getCreditBanance() {
        return creditBanance;
    }

    public void setCreditBanance(Integer creditBanance) {
        this.creditBanance = creditBanance;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public Integer getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(Integer couponCount) {
        this.couponCount = couponCount;
    }
}