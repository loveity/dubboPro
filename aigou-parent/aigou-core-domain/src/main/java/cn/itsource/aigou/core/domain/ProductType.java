package cn.itsource.aigou.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 
 */
public class ProductType implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 类型名
     */
    private String name;

    /**
     * 父ID
     */
    private Long pid;

    /**
     * 图标
     */
    private String logo;

    /**
     * 描述
     */
    private String description;

    private Integer sortIndex;

    /**
     * 路径
     */
    private String path;

    /**
     * 商品数量
     */
    private Integer totalCount;

    /**
     * 分类标题（SEO）
     */
    private String seoTitle;

    /**
     * 分类关键字（SEO）
     */
    private String seoKeywords;
    
    /**
     * 子节点
     */
    private List<ProductType> children = new ArrayList<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getText(){
    	return this.name;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoKeywords() {
        return seoKeywords;
    }

    public void setSeoKeywords(String seoKeywords) {
        this.seoKeywords = seoKeywords;
    }

	public List<ProductType> getChildren() {
		return children;
	}

	public void setChildren(List<ProductType> children) {
		this.children = children;
	}

	public void addChild(ProductType productType){
		this.children.add(productType);
	}
	
	/**
	 * 兼容easyuid tree默认所有非叶子节点为关闭状态
	 * @return
	 */
	public String getState(){
		if(null!=this.getChildren() && this.getChildren().size()>0){
			return "closed";
		}else{
			return "open";
		}
	}
}