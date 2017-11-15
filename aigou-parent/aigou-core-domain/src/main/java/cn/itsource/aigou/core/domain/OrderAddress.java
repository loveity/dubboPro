package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class OrderAddress implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    private Long orderId;

    private String orderSn;

    /**
     * 收货人
     */
    private String reciver;

    /**
     * 区域
     */
    private String areaCode;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 全地址
     */
    private String fullAddress;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 备用手机号
     */
    private String phoneBack;

    /**
     * 固定电话
     */
    private String tel;

    /**
     * 邮编
     */
    private String postCode;

    /**
     * 电子邮件
     */
    private String email;

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneBack() {
        return phoneBack;
    }

    public void setPhoneBack(String phoneBack) {
        this.phoneBack = phoneBack;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}