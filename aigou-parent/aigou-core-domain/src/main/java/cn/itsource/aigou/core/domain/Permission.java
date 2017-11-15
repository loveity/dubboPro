package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class Permission implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 权限编码
     */
    private String code;

    /**
     * 权限名
     */
    private String permissionName;

    /**
     * 父级ID
     */
    private Long pid;

    /**
     * 权限方法全名
     */
    private String methodFullName;

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

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getMethodFullName() {
        return methodFullName;
    }

    public void setMethodFullName(String methodFullName) {
        this.methodFullName = methodFullName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}