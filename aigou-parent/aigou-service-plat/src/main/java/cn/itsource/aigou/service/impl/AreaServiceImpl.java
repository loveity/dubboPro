package cn.itsource.aigou.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.Area;
import cn.itsource.aigou.core.mapper.AreaMapper;
import cn.itsource.aigou.service.IAreaService;
@Service
public class AreaServiceImpl extends BaseServiceImpl<Area> implements IAreaService{
	@Autowired
	private AreaMapper mapper;
	
	@Override
	protected BaseMapper<Area> getMapper() {
		return mapper;
	}

	@Override
	public List<Area> getAreaList(String parentCode) {
		if("0".equals(parentCode)){
			return mapper.getProvinceList();
		}else{
			return mapper.getAreaList(parentCode);
		}
	}

	@Override
	public String getAreaFullNameByCode(String regionCode) {
		if(StringUtils.isBlank(regionCode)) return "";
		String fullName = "";
		String[] codeArr = regionCode.split(",");
		if(codeArr.length>=0){
			long id = Long.parseLong(codeArr[0]+"0000");
			Area area = this.get(id);
			if(null!=area){
				fullName+=area.getName()+" ";
			}
		}
		if(codeArr.length>=1){
			long id = Long.parseLong(codeArr[0]+codeArr[1]+"00");
			Area area = this.get(id);
			if(null!=area){
				fullName+=area.getName()+" ";
			}
		}
		if(codeArr.length>=2){
			long id = Long.parseLong(codeArr[0]+codeArr[1]+codeArr[2]);
			Area area = this.get(id);
			if(null!=area){
				fullName+=area.getName()+" ";
			}
		}
		return fullName;
	}
}
