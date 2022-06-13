package com.douzone.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.douzone.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// 내가 관심있는 파라미터인지 아닌지는 여기서 확인하면됨 
		// authUser 어노테이션 있는지 확인 
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		// authUser 가 안붙어 있으면 
		if(authUser ==null) {
			return false;
		}
		
		// Prameter Type이 UserVo가 아니면 
		if(!parameter.getParameterType().equals(UserVo.class)) {
			return false;
		}
		
		return true;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		//was에서 제공하는거 WebDataBinderFactory 
		// 핸들러 호출되기전에 애가 먼저 호출됨, 파라미터가 넘어올때마다 @AuthUser 가 달려있는지를 검사함 
		
		if(supportsParameter(parameter)) {
			// 내가 관심있는 파라미터가 아닌 경우
			
			return WebArgumentResolver.UNRESOLVED; // null 리턴하는거 해석할 수 없는 아규먼트다 하고 다른애 애게 맡기는거 
		}
		
		HttpServletRequest request =(HttpServletRequest) webRequest.getNativeRequest(); // was가 doget에 넣는거 달라고 하는거 
		HttpSession session =  request.getSession();
		
		if(session==null) { // 로그인 안하고 들어왔을때 
			return null; //authUser를 null로 셋팅 
		}
		
		
		return session.getAttribute("authUser");
	}

}
