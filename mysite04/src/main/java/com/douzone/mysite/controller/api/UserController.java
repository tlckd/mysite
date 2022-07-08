package com.douzone.mysite.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.mysite.dto.JSONResult;
import com.douzone.mysite.service.UserService;
import com.douzone.mysite.vo.UserVo;


@RestController("userApiController")//앞으로 이거쓰기 레스트풀 .. 레스트 컨트롤러 하면 응답할때 리스폰스 바디 안붙여도됨 
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/existemail")   //파라미터로 전달 get방식으로 
	public JSONResult checkEmail(@RequestParam( value="email", required=true, defaultValue = "") String email) {
		UserVo userVo = userService.getUser(email);
		return JSONResult.success(userVo != null); 
	}
}
