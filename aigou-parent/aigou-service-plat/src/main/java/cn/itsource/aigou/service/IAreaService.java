package cn.itsource.aigou.service;

import java.util.List;

import cn.itsource.aigou.core.common.base.IBaseService;
import cn.itsource.aigou.core.domain.Area;

public interface IAreaService extends IBaseService<Area> {

	/**
	 * 通过父CODE获取下级子区域
	 * @param parentCode
	 * @return
	 */
	List<Area> getAreaList(String parentCode);

	
	/**
	 * 通过编码获取全名字
	 * @param regionCode
	 * @return
	 */
	String getAreaFullNameByCode(String regionCode);
	
}
