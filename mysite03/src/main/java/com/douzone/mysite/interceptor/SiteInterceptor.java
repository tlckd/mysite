package com.douzone.mysite.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;

public class SiteInterceptor implements HandlerInterceptor {

	@Autowired
	SiteService siteService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		ServletContext servletContext=request.getServletContext();
		
		if(null==servletContext.getAttribute("site")) {
			SiteVo vo = siteService.getSite();
			servletContext.setAttribute("site", vo);
			return true;
		}
		
		return true;
	}

}
