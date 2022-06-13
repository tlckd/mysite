package com.douzone.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.douzone.mysite.vo.SiteVo;

public class SiteRepository {

	@Autowired 
	private SqlSession sqlSession;
	
	
	private SiteVo find() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne(null);
	}
}
