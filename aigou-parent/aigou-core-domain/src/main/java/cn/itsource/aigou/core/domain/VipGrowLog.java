package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class VipGrowLog implements Serializable {
    private Long id;

    private Long createTime;

    /**
     * 登录用户
     */
    private Long ssoId;

    /**
     * 来源
     */
    private String fromReason;

    /**
     * 成长值
     */
    private Integer score;

    /**
     * 备注
     */
    private String remark;

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

    public String getFromReason() {
        return fromReason;
    }

    public void setFromReason(String fromReason) {
        this.fromReason = fromReason;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}