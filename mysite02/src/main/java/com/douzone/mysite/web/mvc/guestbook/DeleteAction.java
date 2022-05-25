package com.douzone.mysite.web.mvc.guestbook;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.GuestBookRepository;
import com.douzone.mysite.vo.GuestBookVo;
import com.douzone.web.mvc.Action;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String no=request.getParameter("no");
		String password=request.getParameter("password");
		GuestBookVo vo = new GuestBookVo();
		
		
		vo.setNo(Long.parseLong(no));
		vo.setPassword(password);
		PrintWriter out = response.getWriter();
		
		if(new GuestBookRepository().delete(vo)){
			new GuestBookRepository().delete(vo);
			
			out.print("<script>alert('Delete completed.');</script>");
			out.print("<script>location.href ="+"'"+ request.getContextPath() + "/guestbook"  +"'" + "</script>");
			
		}else{
			out.print("<script>alert('Passwords do not match.');</script>");
			out.print("<script>location.href ="+"'"+ request.getContextPath() + "/guestbook"  +"'" + "</script>");
		}
	}

}
