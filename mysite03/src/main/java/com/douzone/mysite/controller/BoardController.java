package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.mysite.web.WebUtil;

@Controller
@RequestMapping("board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@RequestMapping("")
	public String index(Model model,BoardVo vo , 
			@RequestParam(value = "p" , required = true , defaultValue = "1") long page, 
			@RequestParam(value = "kwd" , required = true , defaultValue = "") String keyword) {
				
		Map<String, Object> map = boardService.getContentsList(page, keyword);
		model.addAttribute("map", map);
		//model.addAllAttributes(map);

		return "board/index";
		
	}
	
	@RequestMapping("/view/{no}")
	public String view(HttpSession session,Model model,BoardVo vo, @PathVariable(value = "no") Long no, @RequestParam Long p,@RequestParam String kwd) {
		vo.setNo(no);
		
		model.addAttribute("viewContents",boardService.findViewContents(vo));
		model.addAttribute("page",p);
		model.addAttribute("keyword",kwd);
		
		boardService.hitUpdate(vo);
		
		return "board/view";
	}
	
	@Auth
	@RequestMapping(value = "/writeform")
	public String write(HttpSession session,Model model) {
		
		// 접근 제어(Access Control)
				if(session.getAttribute("authUser") == null) {
					return "redirect:/board";
		}
		////////////////////////////////////////
		
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value = "/write")
	public String write(HttpSession session,Model model,BoardVo vo, HttpServletRequest request, @RequestParam String title, @RequestParam String content) {
		
		vo.setTitle(title);
		vo.setContents(content);		
		boardService.insert(vo);
		

		return "redirect:/board";
	}
	
	@Auth
	@RequestMapping(value = "/write/{no}")
	public String write(HttpSession session,@RequestParam String title, @RequestParam String content,Model model,BoardVo vo, HttpServletRequest request, @PathVariable(value = "no" , required = true) Long boardno ,@RequestParam Long page,@RequestParam String kwd  ) {

		UserVo authUser = (UserVo)session.getAttribute("authUser");

		vo.setTitle(title);		
		boardService.replyInsert(vo,title,content,authUser.getNo());
		

		return "redirect:/board?p=" + page + "&kwd=" + WebUtil.encodeURL(kwd, "UTF-8");
	}
	
	
	@Auth
	@RequestMapping("/delete/{no}")
	public String delete(HttpSession session, @PathVariable("no") Long boardNo, @RequestParam(value = "p", required = true, defaultValue = "1") Integer page, @RequestParam(value = "kwd", required = true, defaultValue = "") String keyword) {

		UserVo authUser = (UserVo)session.getAttribute("authUser");
			
		boardService.deleteContents(boardNo, authUser.getNo());
		return "redirect:/board?p=" + page + "&kwd=" + WebUtil.encodeURL(keyword, "UTF-8");
	}
	
	
	
	@Auth
	@RequestMapping(value = "/modify/{no}")
	public String modify(HttpSession session, @PathVariable("no") Long no, Model model, BoardVo vo) {

		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		BoardVo boardVo = boardService.findViewContents(vo);
		vo.setNo(no);
		model.addAttribute("boardVo", boardVo);
		return "board/modify";
	}
	
	
	@Auth
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(HttpSession session, BoardVo boardVo, @RequestParam(value = "p", required = true, defaultValue = "1") Integer page, @RequestParam(value = "kwd", required = true, defaultValue = "") String keyword, @RequestParam String title, @RequestParam String content) {

		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		boardVo.setUserNo(authUser.getNo());
		boardService.modifyContents(boardVo);
		return "redirect:/board/view/" + boardVo.getNo() + "?p=" + page + "&kwd=" + WebUtil.encodeURL(keyword, "UTF-8");
	}

	
}
