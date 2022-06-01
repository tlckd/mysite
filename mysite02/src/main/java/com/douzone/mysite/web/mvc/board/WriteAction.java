package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession(true);

		if(session==null) { // ACL 접근제어 리턴 잊지말기 
			WebUtil.redirect(request, response, request.getContextPath());
			return;
		}
		
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		String title = request.getParameter("title"); 
		String content = request.getParameter("content");
		String boardNo = request.getParameter("boardno");

		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(content);
		
		if(authUser.getNo() !=null) {
			vo.setUserNo(authUser.getNo());
		}
		
		if("".equals(boardNo)) {
			new BoardRepository().insert(vo);
		}else {
			vo.setNo(Long.parseLong(boardNo));
			vo=new BoardRepository().findCurrentG_noO_noDepth(vo);
			new BoardRepository().insertReplyUpdate(vo);
			new BoardRepository().insertReply(vo);
		}
		
		response.sendRedirect(request.getContextPath()+"/board?p=1");
		
		
	}

}
