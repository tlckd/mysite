package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVo;

@Repository
public class UserRepository {
	public boolean insert(UserVo vo) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = new MyConnection().getConnection();
			
			String sql ="insert into user values(null,?,?,?,?,now());";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
	
			int count = pstmt.executeUpdate();
			result = count == 1;
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;		
	}
	


	public UserVo findByEmailAndPassword(UserVo vo) {
		UserVo result = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			conn = new MyConnection().getConnection();
			
			String sql =
				"select no, name, email" +
				"  from user" +
				" where email=?" +
				"   and password=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getEmail());
			pstmt.setString(2, vo.getPassword());
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Long no = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				
				result = new UserVo();
				result.setNo(no);
				result.setName(name);
				result.setEmail(email);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public UserVo findByNo(Long no) {
		
		UserVo result = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			conn = new MyConnection().getConnection();
			
			String sql ="select no,name,email,gender from user where no=?;";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				no = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String gender = rs.getString(4);
				result = new UserVo();
				result.setNo(no);
				result.setName(name);
				result.setEmail(email);
				result.setGender(gender);
				
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public boolean update(UserVo vo) {
		
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		String sql="";
		String passwordCheck="";
		try {
			connection = new MyConnection().getConnection();
			if("".equals(vo.getPassword())) {
				sql ="update user set name=?, email=?, gender=? where no=?";
				passwordCheck="noPasswordInput";
			}else {
				sql ="update user set name=?, email=?, gender=?,password=? where no=?";
			}
			pstmt = connection.prepareStatement(sql);

			if("noPasswordInput".equals(passwordCheck)) {
				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getEmail());
				pstmt.setString(3, vo.getGender());
				pstmt.setLong(4, vo.getNo());
			}else {
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getGender());
			pstmt.setString(4, vo.getPassword());
			pstmt.setLong(5, vo.getNo());
			}
			int count = pstmt.executeUpdate();
			result = count == 1;
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;	
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
