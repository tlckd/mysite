package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GalleryVo;


@Repository
public class GalleryRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(GalleryVo vo) {
		boolean result= sqlSession.insert("gallery.insert",vo) == 1;
		return result;
	}
	
	public List<GalleryVo> findAll() {
		return sqlSession.selectList("gallery.findAll");		
	}
	
	public boolean delete(Long no) {
		return sqlSession.delete("gallery.delete",no) == 1;
	}
}
