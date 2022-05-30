package com.douzone.mysite.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;

/**
 * Servlet Filter implementation class EncodingFilter
 */
public class EncodingFilter extends HttpFilter implements Filter {

	/**
	 * @see Filter#destroy()
	 */
	//시리얼 버전 id 추가 
	
	private String encoding;
	
	public void init(FilterConfig fConfig) throws ServletException {
		encoding = fConfig.getInitParameter("encoding");
		if(encoding==null) { // null일떄를 대비한 디폴트인코딩 설정해주는거 
			encoding="UTF-8"; 
		}
	}
	
	
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 리퀘스트 처리 
		request.setCharacterEncoding("utf-8");
		
		chain.doFilter(request, response);
		
		//리스폰스 처리
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */


}
