package com.douzone.mysite.web.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.UserRepository;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		
		if(session==null) { // ACL 접근제어 리턴 잊지말기 
			WebUtil.redirect(request, response, request.getContextPath());
			return;
		}
		
		UserVo userVo=(UserVo)session.getAttribute("userVo");
		UserVo authVo=(UserVo)session.getAttribute("authUser");
		
		if(userVo ==null) {
			WebUtil.redirect(request, response, request.getContextPath());
			return;
		}
		
		Long no = userVo.getNo();

		String name =request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		
		userVo.setName(name);
		userVo.setPassword(password);
		userVo.setGender(gender);
		new UserRepository().update(userVo);
		
		authVo.setName(name);
		
		session.setAttribute("userVo", userVo);
		session.setAttribute("authUser", authVo);
		
		WebUtil.redirect(request, response, request.getContextPath()+"/user?a=updateform");
		
		
		
		
		
	}

}
