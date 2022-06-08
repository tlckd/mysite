package com.douzone.mysite.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExcepitonHandler {
	
	@ExceptionHandler(Exception.class)
	public String handlerException(Model model,Exception e) {
		//1.로깅(logging) 
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors)); //이거를 파일로 저장하면됨 
		e.printStackTrace();
		
		//2.사과 페이지(종료)  
		model.addAttribute("exception",errors.toString());
		return "error/exception";
	}
}
