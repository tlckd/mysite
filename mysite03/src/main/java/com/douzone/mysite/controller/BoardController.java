package com.douzone.mysite.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@RequestMapping("")
	public String index(Model model,BoardVo vo , 
			@RequestParam(value = "p" , required = true , defaultValue = "1") long page, 
			@RequestParam(value = "kwd" , required = true , defaultValue = "") String kwd) {
		
		vo.setPage(page);
		vo.setKwd("%"+kwd+"%");
		
		model.addAttribute("page",page);
		model.addAttribute("kwd",kwd);
		model.addAttribute("boardList",boardService.findAll(vo));
		model.addAttribute("boardCount",boardService.findBoardCount(vo));
		return "board/index";
		
	}
	
	@RequestMapping("/view/{no}")
	public String view(Model model,BoardVo vo, @PathVariable(value = "no") Long no) {
		vo.setNo(no);
		
		model.addAttribute("viewContents",boardService.findViewContents(vo));
		boardService.hitUpdate(vo);
		
		return "board/view";
	}
	
	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(BoardVo vo, Model model) {
		
		
		model.addAttribute("vo",vo);
		return "board/write";
	}
	
	
	@RequestMapping(value = "/write/{no}", method = RequestMethod.POST)
	public String write(Model model,BoardVo vo, HttpServletRequest request, @RequestParam String title, @RequestParam String contents, @PathVariable(value = "no" , required = false) Long boardno ) {
		
		
		HttpSession session = request.getSession(true);
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		vo.setTitle(title);
		vo.setContents(contents);
		
		boardService.insert(vo, boardno, authUser.getNo());
		

		return "board/index";
	}
	
	
}
