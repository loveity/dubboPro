package cn.itsource.aigou.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;

import cn.itsource.aigou.core.common.base.BaseMapper;
import cn.itsource.aigou.core.common.base.impl.BaseServiceImpl;
import cn.itsource.aigou.core.consts.GlobalSettingNames;
import cn.itsource.aigou.core.domain.ProductType;
import cn.itsource.aigou.core.mapper.ProductTypeMapper;
import cn.itsource.aigou.core.utils.GlobalSetting;
import cn.itsource.aigou.core.utils.VelocityUtils;
import cn.itsource.aigou.facade.CommonService;
import cn.itsource.aigou.service.IProductTypeService;

@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductType> implements IProductTypeService {
	@Autowired
	private ProductTypeMapper mapper;

	@Reference
	private CommonService commonService;

	@Override
	protected BaseMapper<ProductType> getMapper() {
		return mapper;
	}

	@Override
	public void save(ProductType o) {
		super.save(o);
		this.handleSave(o);
		this.updatePart(o);
		this.staticCat();
	}

	@Override
	public void update(ProductType o) {
		this.handleSave(o);
		super.update(o);
		this.staticCat();
	}

	@Override
	public void updatePart(ProductType o) {
		this.handleSave(o);
		super.updatePart(o);
		this.staticCat();
	}

	/**
	 * 存储修改分类前进行预处理数据
	 * 
	 * @param o
	 */
	private void handleSave(ProductType o) {
		Long id = o.getId();
		Long pid = o.getPid();
		String path = "";
		if (pid == null || 0 == pid) {// 一级分类
			path = "." + id + ".";
		} else {// 子类
			ProductType parentType = this.get(pid);
			path = parentType.getPath() + id + ".";
		}
		o.setPath(path);
	}

	/**
	 * 分类修改后静态化分类信息
	 */
	private void staticCat() {
		// 静态化生成新的json数据，并上传到七牛云空间
		List<ProductType> productTypes = this.getAllTree(0L);
		String jsonData = JSONObject.toJSONString(productTypes);
		commonService.uploadToQiniuCdn(jsonData.getBytes(), "types.json", null);

		// 静态化商品分类
		String staticRoot = GlobalSetting.get(GlobalSettingNames.WWW_WEB_STATIC_DIR);
		String catTemplateFile = staticRoot + GlobalSetting.get(GlobalSettingNames.WWW_WEB_STATIC_CAT_TEMPLATE);
		VelocityUtils.staticByTemplate(productTypes, catTemplateFile, catTemplateFile + ".html");
		// 静态化首页
		String homeTemplateFile = staticRoot + GlobalSetting.get(GlobalSettingNames.WWW_WEB_STATIC_HOME_TEMPLATE);
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("staticRoot", staticRoot);
		VelocityUtils.staticByTemplate(modelMap, homeTemplateFile, staticRoot + "home.html");
	}

	@Override
	public List<ProductType> getAll() {
		return mapper.getAll();
	}

	@Override
	public List<ProductType> getAllTree(Long pid) {
		// return getAllTreeByRec(pid);
		return getAllTreeByLoop(pid);
	}

	/**
	 * 通过递归获取分类树（数据量大时，效率低）
	 * 
	 * @param pid
	 * @return
	 */
	private List<ProductType> getAllTreeByRec(Long pid) {
		List<ProductType> childList = mapper.getChildren(pid);
		if (childList == null || childList.size() == 0) {
			return null;
		}
		for (ProductType productType : childList) {
			List<ProductType> children = this.getAllTree(productType.getId());
			if (children != null) {
				productType.setChildren(children);
			}
		}
		return childList;
	}

	/**
	 * 通过循环获取分类树
	 * 
	 * @param pid
	 * @return
	 */
	private List<ProductType> getAllTreeByLoop(Long pid) {
		List<ProductType> orginals = null;
		if (pid == 0) {
			orginals = mapper.getAll();
		} else {
			orginals = mapper.getTypes(pid);
		}
		if (null == orginals)
			return null;
		Map<Long, ProductType> dtoMap = new HashMap<Long, ProductType>();
		for (ProductType node : orginals) {
			dtoMap.put(node.getId(), node);
		}

		List<ProductType> resultList = new ArrayList<ProductType>();
		for (Map.Entry<Long, ProductType> entry : dtoMap.entrySet()) {
			ProductType node = entry.getValue();
			if (node.getPid() == pid) {
				// 如果是顶层节点，直接添加到结果集合中
				resultList.add(node);
			} else {
				// 如果不是顶层节点，找的起父节点，然后添加到父节点的子节点中
				if (dtoMap.get(node.getPid()) != null) {
					dtoMap.get(node.getPid()).addChild(node);
				}
			}
		}
		return resultList;
	}

	@Override
	public List<Map<String, Object>> getTypeBreadcrumb(Long productTypeId) {
		List<Map<String, Object>> breadCrumbList = new ArrayList<>();
		ProductType currentType = this.get(productTypeId);
		String path = currentType.getPath();
		path = path.substring(1, path.length() - 1);
		String[] pathArr = path.split("\\.");
		for (int i = 0; i < pathArr.length; i++) {
			Map<String, Object> breadCrumbMap = new HashMap<>();
			Long typeId = Long.parseLong(pathArr[i]);
			long pid = typeId;
			//根节点
			if(0==i){
				pid = 0L;
			}else{
				pid = Long.parseLong(pathArr[i-1]);
			}
			//获取父节点所有的子节点
			List<ProductType> types = mapper.getChildren(pid);
			int j = 0;
			for (; j < types.size(); j++) {
				if (types.get(j).getId() == typeId.longValue()) {
					break;
				}
			}
			breadCrumbMap.put("currentType", types.get(j));
			types.remove(j);
			breadCrumbMap.put("otherTypes", types);
			breadCrumbList.add(breadCrumbMap);
		}
		return breadCrumbList;
	}
}
