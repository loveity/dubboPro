package cn.itsource.aigou.core.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class AdArea implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 位置编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 图片地址
     */
    private String pic;

    /**
     * 链接地址
     */
    private String url;

    /**
     * 背景
     */
    private String background;

    /**
     * 排序
     */
    private Integer sortIndex;

    /**
     * 说明
     */
    private String description;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Integer getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}