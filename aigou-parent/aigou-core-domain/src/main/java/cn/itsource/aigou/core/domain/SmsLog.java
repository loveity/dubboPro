package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class SmsLog implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 内容
     */
    private String content;

    /**
     * 发送时间
     */
    private Long sendTime;

    /**
     * 月份
     */
    private Short month;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public Short getMonth() {
        return month;
    }

    public void setMonth(Short month) {
        this.month = month;
    }
}