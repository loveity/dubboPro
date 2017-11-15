package cn.itsource.aigou.web.controller;

import cn.itsource.aigou.core.consts.GlobalSettingNames;
import cn.itsource.aigou.core.utils.GlobalSetting;
import cn.itsource.aigou.facade.CommonService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@Controller
public class UeditorController {
	@Reference
	private CommonService commonService;
	
	@RequestMapping("/plugin/ueditor")
	@ResponseBody
	public Map<String, Object> ueditor(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		// 设置编码
		multipartResolver.setDefaultEncoding("utf-8");
		// 判断 request 是否有文件上传,即含有文件流数据的请求...
		if (multipartResolver.isMultipart(request)) {
			// 转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			// 获得多文件的迭代器
			Iterator iter = multiRequest.getFileNames();
			if (iter.hasNext()) {
				// 根据名称 获取上传的文件...
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					//创建七牛云空间的KEY为uuid
					String key = UUID.randomUUID().toString().replace("-", "");
					//获取上传文件大小
					long size = file.getSize();
					//获取原始文件名
					String originalFilename = file.getOriginalFilename();

					//创建返回给ueditor上传控件的响应数据，最终以json格式返回
					Map<String, Object> retMap = new HashMap<String, Object>();
					retMap.put("state", "SUCCESS");
					retMap.put("title", "" + originalFilename);
					retMap.put("original", "" + originalFilename);
					retMap.put("size", size + "");
					
					//获取上传文件流
					InputStream upInputStream = file.getInputStream();
					//调用工具方法，通过java sdk直接上传到七牛云空间
					String retKey = commonService.uploadToQiniuCdn(cn.itsource.aigou.web.utils.FileUtils.stream2Byte(upInputStream), key, 
							null);
					//返回ueditor可以访问到的上传后的图片地址
					retMap.put("url", GlobalSetting.get(GlobalSettingNames.QINIU_DOMAIN) + retKey);
					return retMap;
				}
			}
		} else {//其它请求
			String action = request.getParameter("action");
			//如果请求配置信息，直接返回ueditor的配置文件config.json中的内容
			if ("config".equals(action)) {
				String ueditorJsonFilePath;
				ueditorJsonFilePath = request.getServletContext()
						.getRealPath("/easyui/plugin/ueditor/jsp/config.json");
				String ueditorConfigJson = "";
				ueditorConfigJson = FileUtils.readFileToString(new File(ueditorJsonFilePath), "utf-8");
				response.getWriter().write(ueditorConfigJson);
			}
		}

		return null;
	}
}
