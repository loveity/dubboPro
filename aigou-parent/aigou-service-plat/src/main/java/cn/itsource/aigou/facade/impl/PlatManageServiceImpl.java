package cn.itsource.aigou.facade.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;

import cn.itsource.aigou.core.domain.Area;
import cn.itsource.aigou.core.domain.Employee;
import cn.itsource.aigou.core.domain.Seckill;
import cn.itsource.aigou.core.domain.SeckillSku;
import cn.itsource.aigou.core.utils.Page;
import cn.itsource.aigou.facade.PlatManageService;
import cn.itsource.aigou.facade.query.QuartzJobInfo;
import cn.itsource.aigou.facade.query.SeckillQuery;
import cn.itsource.aigou.service.IAreaService;
import cn.itsource.aigou.service.IEmployeeService;
import cn.itsource.aigou.service.ISeckillService;
import cn.itsource.aigou.service.ISeckillSkuService;

@Service
public class PlatManageServiceImpl implements PlatManageService {

	@Autowired
	private IEmployeeService employeeService;
	
	@Autowired
	private IAreaService areaService;
	
	@Autowired
	private ISeckillService seckillService;
	
	@Autowired
	private ISeckillSkuService seckillSkuService;
	
	public Employee getEmployee(Long id) {
		return employeeService.get(id);
	}

	@Override
	public Map<String, Object> getRegionListMap(String regionCode) {
		Map<String, Object> retMap = new HashMap<>();
		Map<String, Object> namesMap = new HashMap<>();
		List<List<Map<String, Object>>> allList = new ArrayList<>();

		retMap.put("code", 0);

		if (StringUtils.isNotBlank(regionCode)) {
			String[] reginCodeArr = regionCode.split(",");

			String parentCode = "0";
			List<Area> provinceList = areaService.getAreaList(parentCode);
			List<Map<String, Object>> provinceMapList = convertToRegionPluginData(provinceList);
			allList.add(provinceMapList);
			for (Area area : provinceList) {
				if (area.getCode().equals(reginCodeArr[0])) {
					namesMap.put(area.getCode(), area.getName());
				}
			}
			if (reginCodeArr.length >= 1) {
				parentCode = reginCodeArr[0];
				List<Area> cityList = areaService.getAreaList(parentCode);
				List<Map<String, Object>> cityMapList = convertToRegionPluginData(cityList);
				allList.add(cityMapList);

				for (Area area : cityList) {
					if (area.getCode().equals(reginCodeArr[0] + "," + reginCodeArr[1])) {
						namesMap.put(area.getCode(), area.getName());
					}
				}

				if (reginCodeArr.length >= 2) {
					parentCode = reginCodeArr[0] + "," + reginCodeArr[1];
					List<Area> areaList = areaService.getAreaList(parentCode);
					List<Map<String, Object>> areaMapList = convertToRegionPluginData(areaList);
					allList.add(areaMapList);
					if (reginCodeArr.length >= 3) {
						for (Area area : areaList) {
							if (area.getCode()
									.equals(reginCodeArr[0] + "," + reginCodeArr[1] + "," + reginCodeArr[2])) {
								namesMap.put(area.getCode(), area.getName());
							}
						}
					}
				}
			}
			retMap.put("region_names", namesMap);
		} 

		retMap.put("data", allList);
		String[] levelNames = { "", "省", "市", "区/县", "镇", "街道/村" };
		retMap.put("level_names", levelNames);
		return retMap;
	}

	@Override
	public Map<String, Object> getRegionChildrenListMap(String parentCode) {
		Map<String, Object> retMap = new HashMap<>();
		List<List<Map<String, Object>>> allList = new ArrayList<>();

		retMap.put("code", 0);
		if (StringUtils.isNotBlank(parentCode)) {
			List<Area> areaList = areaService.getAreaList(parentCode);
			List<Map<String, Object>> areaMapList = convertToRegionPluginData(areaList);
			if (null != areaMapList) {
				allList.add(areaMapList);
			}
		}

		retMap.put("data", allList);
		String[] levelNames = { "", "省", "市", "区/县", "镇", "街道/村" };
		retMap.put("level_names", levelNames);
		return retMap;
	}
	
	@Override
	public String getRegionFullName(String regionCode) {
		return areaService.getAreaFullNameByCode(regionCode);
	}


	
	/**
	 * 转换为jquery.region插件适配的格式
	 * 
	 * @param areaList
	 * @return
	 */
	private List<Map<String, Object>> convertToRegionPluginData(List<Area> areaList) {
		if (areaList == null || areaList.size() == 0)
			return null;
		List<Map<String, Object>> mapList = new ArrayList<>();
		for (Area area : areaList) {
			Map<String, Object> areaMap = new HashMap<>();
			areaMap.put("region_id", area.getId());
			areaMap.put("region_code", area.getCode());
			areaMap.put("region_name", area.getName());
			if (area.getCode().contains(",")) {
				areaMap.put("parent_code", area.getCode().substring(0, area.getCode().lastIndexOf(",")));
			} else {
				areaMap.put("parent_code", "0");
			}
			areaMap.put("level", area.getLevel());
			mapList.add(areaMap);
		}
		return mapList;
	}

	@Override
	public Page<Seckill> seckillPage(SeckillQuery qObject) {
		return seckillService.queryPage(qObject);
	}

	@Override
	public Seckill seckillGet(Long id) {
		return seckillService.get(id);
	}

	@Override
	public void seckillStore(Seckill seckill) {
		seckillService.saveOrUpdate(seckill);
	}

	@Override
	public List<SeckillSku> seckillGetSkus(Long seckillId) {
		return seckillService.getSkuList(seckillId);
	}

	@Override
	public void seckillPublish(Long[] seckillIdArr) {
		seckillService.publish(seckillIdArr);
	}

	@Override
	public void seckillPreHandle(QuartzJobInfo info) {
		seckillService.preHandle(info);
	}

	@Override
	public void seckillBeginHandle(QuartzJobInfo info) {
		seckillService.beginHandle(info);
	}

	@Override
	public void seckillEndHandle(QuartzJobInfo info) {
		seckillService.endHandle(info);
	}

	@Override
	public void seckillExpireResult(QuartzJobInfo info) {
		seckillService.expireResult(info);
	}

	@Override
	public boolean inSechitQueue(Long ssoId, Long seckillSkuId) {
		return seckillService.inHitQueue(ssoId,seckillSkuId);
	}

}
