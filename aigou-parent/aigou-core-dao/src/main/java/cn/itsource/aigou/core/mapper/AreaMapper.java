package cn.itsource.aigou.core.mapper;

import java.util.List;

import cn.itsource.aigou.core.domain.Area;

public interface AreaMapper extends BaseMapper<Area> {

	/**
	 * 通过父CODE获取下级子区域
	 * @param parentCode
	 * @return
	 */
	List<Area> getAreaList(String parentCode);

	/**
	 * 获取全部省的列表
	 * @return
	 */
	List<Area> getProvinceList();
	
}