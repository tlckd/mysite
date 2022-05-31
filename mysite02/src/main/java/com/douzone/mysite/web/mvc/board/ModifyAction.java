package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String boardno = request.getParameter("boardno");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		BoardVo vo = new BoardVo(); 
		
		vo.setNo(Long.parseLong(boardno));
		vo.setTitle(title);
		vo.setContents(content);
		
		new BoardRepository().contentsModify(vo);
		
		response.sendRedirect(request.getContextPath()+"/board");
		
	}

}
