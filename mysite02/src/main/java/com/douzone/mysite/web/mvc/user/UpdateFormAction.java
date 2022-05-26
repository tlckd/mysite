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

public class UpdateFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session==null) { // url로 치고왔을때 접근제어 인증이 필요한 요청에는 무조건 이코드 붙여줘야함 말그대로 접근제어 ACL 게시판 글쓰기 같은데 쓰임 
			WebUtil.redirect(request, response, request.getContextPath());
			return; // 리다이렉트하면 응답은했지만 코드상으로는 끝이안난거 return으로 끝을내야 오류가 안남 응답2번하면 오류나니 꼭 리턴 ㄱㄱ 
		}
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		if(authUser ==null) {
			WebUtil.redirect(request, response, request.getContextPath());
			return;
		}
		
		//UserVo userVo =new UserRepository().findByNo(authUser.getNo);
		WebUtil.forward(request, response, "user/updateform");

	}

}
