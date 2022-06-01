package com.douzone.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.repository.GuestBookRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.GuestBookVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class IndexAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page=request.getParameter("p");
		String kwd=request.getParameter("kwd");

		if(page== null) {
			page="1";
		}
		if(kwd==null) {
			kwd="";
		}
		
		List <BoardVo> boardList = new BoardRepository().findAll(Long.parseLong(page),kwd);
		BoardVo vo = new BoardVo();
		vo = new BoardRepository().findBoardCount(vo,kwd);
		request.setAttribute("boardCount",vo.getBoardCount());
		request.setAttribute("boardList", boardList);
		WebUtil.forward(request, response, "board/index");
		
		
	}

}
