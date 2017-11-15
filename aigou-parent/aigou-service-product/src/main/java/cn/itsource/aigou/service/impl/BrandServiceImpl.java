package cn.itsource.aigou.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.domain.Brand;
import cn.itsource.aigou.core.mapper.BrandMapper;
import cn.itsource.aigou.core.utils.LetterUtil;
import cn.itsource.aigou.service.IBrandService;
@Service
public class BrandServiceImpl extends BaseServiceImpl<Brand> implements IBrandService{
	@Autowired
	private BrandMapper mapper;
	
	@Override
	protected BaseMapper<Brand> getMapper() {
		return mapper;
	}
	
	@Override
	public void update(Brand o) {
		this.handleSave(o);
		super.update(o);
	}
	
	@Override
	public void updatePart(Brand o) {
		this.handleSave(o);
		super.updatePart(o);
	}
	
	@Override
	public void save(Brand o) {
		this.handleSave(o);
		super.save(o);
	}
	
	@Override
	public void savePart(Brand o) {
		this.handleSave(o);
		super.savePart(o);
	}
	
	private void handleSave(Brand o){
		o.setFirstLetter(LetterUtil.getFirstLetter(o.getName()).toUpperCase());
	}

	@Override
	public List<Brand> getBrandsByProductType(Long productType) {
		return mapper.getBrandsByProductType(productType);
	}
}
