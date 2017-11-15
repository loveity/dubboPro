package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class VipLoginLog implements Serializable {
    private Long id;

    private Long createTime;

    private Long ssoId;

    /**
     * IP
     */
    private String ip;

    /**
     * 客户端
     */
    private String clientInfo;

    /**
     * 登录方式
     */
    private Byte loginType;

    /**
     * 登录是否成功
     */
    private Byte success;

    /**
     * 结果说明
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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public Byte getLoginType() {
        return loginType;
    }

    public void setLoginType(Byte loginType) {
        this.loginType = loginType;
    }

    public Byte getSuccess() {
        return success;
    }

    public void setSuccess(Byte success) {
        this.success = success;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}