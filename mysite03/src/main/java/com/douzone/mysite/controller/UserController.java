package com.douzone.mysite.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.security.AuthUser;
import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo userVo, BindingResult result, Model model) {
		if(result.hasErrors()) {
			//검증 통과 못했을때 다시 데이터 받게 폼으로 이동, 사용자가 입력한 데이터들도 보관해놨다가 넣어줘야함 다시 입력안해도 되게 
//			List<ObjectError> list = result.getAllErrors();// 에러발생한 내역을 리스트에 저장 
//			for(ObjectError error : list) {
//				System.out.println(error);
//			}
			model.addAllAttributes(result.getModel());//하나하나 get해서 맵으로 만들어줌 
			//model.addAttribute("userVo", vo);
			return "user/join";
		}
		//userService.join(vo);
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		return "user/login";
	}

	@RequestMapping(value="/auth")
	public void auth() {
	}

	@RequestMapping("/logout")
	public void logout() {
	}

	@Auth
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, Model model) {
		Long no = authUser.getNo();
		UserVo userVo = userService.getUser(no);
		
		model.addAttribute("userVo", userVo);
		return "user/update";
	}

	@Auth
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@AuthUser UserVo authUser, UserVo vo) {
		vo.setNo(authUser.getNo());
		userService.updateUser(vo);
		authUser.setName(vo.getName());
		
		return "redirect:/user/update";
	}		
}
