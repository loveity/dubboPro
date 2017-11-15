package cn.itsource.aigou.core.domain;

import java.io.Serializable;
import java.util.List;


/**
 * @author 
 */
public class Seckill implements Serializable {
    private Long id;

    private Long createTime;

    private Long updateTime;

    /**
     * 标题
     */
    private String title;

    /**
     * 开始时间
     */
    private Long beginTime;

    /**
     * 结束时间
     */
    private Long endTime;

    /**
     * 状态
     */
    private Byte state;

    /**
     * 说明
     */
    private String description;

    /**
     * 活动背景图
     */
    private String activePic;
    
    /**
     * 抢购商品列表
     */
    private List<SeckillSku> seckillSkuList;
    

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getActivePic() {
        return activePic;
    }

    public void setActivePic(String activePic) {
        this.activePic = activePic;
    }
    
    public List<SeckillSku> getSeckillSkuList() {
		return seckillSkuList;
	}
    
    public void setSeckillSkuList(List<SeckillSku> seckillSkuList) {
		this.seckillSkuList = seckillSkuList;
	}
}