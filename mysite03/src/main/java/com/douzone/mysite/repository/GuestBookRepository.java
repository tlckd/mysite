package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.GuestbookRepositoryException;
import com.douzone.mysite.vo.GuestBookVo;


@Repository
public class GuestBookRepository {

	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private DataSource dataSource;
	
	
	
	public boolean insert(GuestBookVo vo) {
		System.out.println(vo);
		boolean result= sqlSession.insert("guestbook.insert",vo) == 1;		
		System.out.println(vo);
		return result;
	}
	
	
	public boolean delete(Long no, String password) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("password", password);
		return sqlSession.delete("guestbook.delete",map) == 1;
	}
	
	
	public List<GuestBookVo> findAll() {
		
		return sqlSession.selectList("guestbook.findAll");		
	}
	
	
	
//	private Connection getConnection() throws SQLException {
//		Connection connection = null;
//		
//		try {
//			Class.forName("org.mariadb.jdbc.Driver");
//			String url = "jdbc:mysql://192.168.35.153:3306/webdb?charset=utf8";
//			connection = DriverManager.getConnection(url, "webdb", "webdb");
//		} catch (ClassNotFoundException e) {
//			System.out.println("드라이버 로딩 실패:" + e);
//		}
//		
//		return connection;
//	}	
	
	
}
