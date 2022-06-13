package com.douzone.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.UserRepository;
import com.douzone.mysite.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public void join(UserVo vo) {
		userRepository.insert(vo);
	}

	public UserVo getUser(UserVo vo) {
		return userRepository.findByEmailAndPassword(vo);
	}

	public UserVo getUser(Long no) {
	
		return userRepository.findByNo(no);
	}
	
	public UserVo getUser(String email, String password) {
		UserVo vo = new UserVo();
		vo.setEmail(email);
		vo.setPassword(password);
		
		return getUser(vo);
		// 기존에 있는애 쓰기 오버로드 했을때 수정할때 한곳만 수정하게 
	}
	
	
	public void updateUser(UserVo vo) {
		userRepository.update(vo);
		
	}

	
}
