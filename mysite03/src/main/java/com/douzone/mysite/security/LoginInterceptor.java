package com.douzone.mysite.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

public class LoginInterceptor implements HandlerInterceptor {

	//new 말고 DI 주입방식으로 ㄱㄱ new로 하면 userRepository가 주입안되어 있음 스프링 컨테이너가 Di 해준 객체를 사용 ㄱㄱ 
	//new 쓰지말기.. vo 말고 ㅇㅇ vo는 싱글톤으로 쓰면 안됨 관계 가진애만 그런거 
	@Autowired
	private UserService userService;
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVo authUser = userService.getUser(email, password);
		if(authUser == null) {
			request.setAttribute("email", email);
			request.setAttribute("result", "fail");
			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
			return false;
		}

		/* session 처리 */
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);

		response.sendRedirect(request.getContextPath());
		return false;
	}

}
