package cn.itsource.aigou.core.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class VipBase implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 登录账号
     */
    private Long ssoId;

    /**
     * 注册渠道
     */
    private Byte regChannel;

    /**
     * 注册时间
     */
    private Long regTime;

    /**
     * 激活时间
     */
    private Long activeTime;

    /**
     * 激活方式
     */
    private Byte activeType;

    /**
     * QQ
     */
    private String qq;

    private Byte level;

    /**
     * 成长值
     */
    private Integer growScore;

    /**
     * 推荐人
     */
    private Long referId;

    /**
     * 性别
     */
    private Byte sex;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 居住地区域
     */
    private Integer areaCode;

    /**
     * 详细地址
     */
    private String address;

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

    public Byte getRegChannel() {
        return regChannel;
    }

    public void setRegChannel(Byte regChannel) {
        this.regChannel = regChannel;
    }

    public Long getRegTime() {
        return regTime;
    }

    public void setRegTime(Long regTime) {
        this.regTime = regTime;
    }

    public Long getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Long activeTime) {
        this.activeTime = activeTime;
    }

    public Byte getActiveType() {
        return activeType;
    }

    public void setActiveType(Byte activeType) {
        this.activeType = activeType;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public Integer getGrowScore() {
        return growScore;
    }

    public void setGrowScore(Integer growScore) {
        this.growScore = growScore;
    }

    public Long getReferId() {
        return referId;
    }

    public void setReferId(Long referId) {
        this.referId = referId;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}