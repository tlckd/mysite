package com.douzone.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.mysite.vo.UserVo;

public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 1. 핸들러 종류 확인 
		if(handler instanceof HandlerMethod == false) {
			//디폴트 서블릿 핸들러가 처리하는 정적자원
			return true; 
		}
		
		//2. casting 해서 핸들러 정보를 가져옴 
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		// 3. 컨트롤러 핸들러 메소드의 @Auth 받아보기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		// 4.컨트롤러의 핸들러 메소드에 @Auth가 안붙어있는 경우
		if(auth==null) {
			return true; 
		}
		
		// 5. 핸들러 메소드에 @Auth가 붙어 있기 떄문에 인증 여부 확인 
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		// 6.  @Auth는 적용되어 있고 인증은 되어있지 않음 
		if(authUser == null) {
			response.sendRedirect(request.getContextPath()+"/user/login");
			return false;
		}
		
		// 7. @Auth도 적용되어 있고 인증도 되어 있음  
		return true;
	}

}
