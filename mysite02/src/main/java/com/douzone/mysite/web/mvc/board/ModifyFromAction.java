package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class ModifyFromAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardVo vo = new BoardVo();
		String no = request.getParameter("boardno");
		Long lno = Long.parseLong(no);
		
		vo.setNo(lno);
		vo = new BoardRepository().findViewContents(vo);
		new BoardRepository().hitUpdate(vo);
		
		request.setAttribute("viewContents", vo);
		
		WebUtil.forward(request, response, "board/modify");

	}

}
