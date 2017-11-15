package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class Sso implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 三方登录名
     */
    private String thirdUid;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    private String avatar;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户状态
     */
    private Long bitState;
    
    /**安全等级*/
    private Byte secLevel = 0;

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

    public String getThirdUid() {
        return thirdUid;
    }

    public void setThirdUid(String thirdUid) {
        this.thirdUid = thirdUid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getBitState() {
        return bitState;
    }

    public void setBitState(Long bitState) {
        this.bitState = bitState;
    }

    public Byte getSecLevel() {
        return secLevel;
    }

    public void setSecLevel(Byte secLevel) {
        this.secLevel = secLevel;
    }
}