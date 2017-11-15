package cn.itsource.aigou.web.handler;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.alibaba.fastjson.JSON;

import cn.itsource.aigou.core.exception.BisException;
import cn.itsource.aigou.core.utils.Ret;

public class GlobalExceptionHandler extends SimpleMappingExceptionResolver {
	public static final String EXCEPTION_INFO_NAME_IN_VIEW = "exp";
	public static final String DEFAULT_ERR_PAGE = "error";
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView modelAndView = new ModelAndView();
		String header = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equalsIgnoreCase(header);
		BisException bisException;
		Ret ret = null;
        if(ex instanceof BisException) {
        	bisException = (BisException) ex;
        } else {
        	bisException = BisException.me();
        }
        ret = bisException.getRet();
        ex.printStackTrace();
        
        if(!isAjax){
	        modelAndView.addObject(GlobalExceptionHandler.EXCEPTION_INFO_NAME_IN_VIEW, ret);
	        modelAndView.setViewName(GlobalExceptionHandler.DEFAULT_ERR_PAGE);
	        return modelAndView;
        }
        
        String retJson = JSON.toJSONString(ret);
        
        try {
        	response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(retJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return new ModelAndView();
	}
	
}
