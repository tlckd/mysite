package com.douzone.mysite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.web.mvc.main.MainActionFactory;
import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;


public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	
	@Override
	public void init() throws ServletException {
		this.getServletConfig();
		super.init();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//RequestDispatcher rd= request.getRequestDispatcher("/WEB-INF/views/main/index.jsp");
		//rd.forward(request, response);
		
		//request.setCharacterEncoding("utf-8");
		//Encoding Filter에서 처리 
		String actionName = request.getParameter("a");
		
		ActionFactory factory = new MainActionFactory();
		Action action =factory.getAction(actionName);
		action.execute(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
