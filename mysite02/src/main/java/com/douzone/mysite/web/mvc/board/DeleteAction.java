package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String boardNo = request.getParameter("boardno");
		BoardVo vo = new BoardVo(); 
		
		vo.setNo(Long.parseLong(boardNo));
		new BoardRepository().deleteBoard(vo);
		
		response.sendRedirect(request.getContextPath()+"/board");
	}

}
