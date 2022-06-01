package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.GuestBookVo;

public class BoardRepository {
	
	public boolean insert(BoardVo vo) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = new MyConnection().getConnection();
			
			String sql ="insert into board values(null,?,?,0,now(),(select ifnull(max(g_no),0)+1 from board b),1,1,?);";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getUserNo());
			
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
	
	
	public boolean insertReplyUpdate(BoardVo vo) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = new MyConnection().getConnection();
			
			String sql ="update board set o_no=?+1 where g_no=? and o_no>=?+1;";
			pstmt = connection.prepareStatement(sql);

			pstmt.setLong(1, vo.getoNo());
			pstmt.setLong(2, vo.getgNo());
			pstmt.setLong(3, vo.getoNo());
			
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
	
	
	public boolean insertReply(BoardVo vo) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = new MyConnection().getConnection();
			
			String sql ="insert into board values(null,?,?,0,now(),?,?+1,?+1,?);";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getgNo());
			pstmt.setLong(4, vo.getoNo());
			pstmt.setLong(5, vo.getDepth());
			pstmt.setLong(6, vo.getUserNo());
			
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
	
	public List<BoardVo> findAll(Long page,String kwd) {
		List<BoardVo> result = new ArrayList<>();
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = new MyConnection().getConnection();
			
			//3. SQL 준비
			String sql ="select a.no,title,contents,hit,date_format(reg_date, '%Y-%m-%d %H:%i:%s'),g_no,o_no,depth,a.user_no,b.name"
					+ " from board a, user b"
					+ " where a.user_no = b.no and (title like ? or contents like ?)"
					+ " order by g_no desc , o_no asc"
					+ " limit ?,5;";
			pstmt = connection.prepareStatement(sql);
			
			//4. Parameter Mapping
			pstmt.setString(1, "%"+kwd+"%");
			pstmt.setString(2, "%"+kwd+"%");
			pstmt.setLong(3, (page-1)*5);


			//5. SQL 실행
			rs = pstmt.executeQuery();
			
			//6. 결과처리
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long hit = rs.getLong(4);
				String date = rs.getString(5);
				Long gNo = rs.getLong(6);
				Long oNo = rs.getLong(7);
				Long depth = rs.getLong(8);
				Long userNo = rs.getLong(9);
				String name = rs.getString(10);
				BoardVo vo = new BoardVo();
				
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(date);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserNo(userNo);
				vo.setName(name);
				
				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
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
	
	public BoardVo findViewContents(BoardVo vo) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = new MyConnection().getConnection();
			
			//3. SQL 준비
			String sql ="select no,title,contents,hit,user_no  from board where no=?";
			pstmt = connection.prepareStatement(sql);
			
			//4. Parameter Mapping
			pstmt.setLong(1, vo.getNo());
			
			//5. SQL 실행
			rs = pstmt.executeQuery();
			
			//6. 결과처리
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long hit = rs.getLong(4);
				Long userNo = rs.getLong(5);
				
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setUserNo(userNo);
				
			}
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
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
		
		return vo;		
	}
	
	public BoardVo findCurrentG_noO_noDepth(BoardVo vo) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			connection = new MyConnection().getConnection();
			
			//3. SQL 준비
			String sql ="select no, g_no, o_no, depth from board where no=?;";
			pstmt = connection.prepareStatement(sql);
			
			//4. Parameter Mapping
			pstmt.setLong(1, vo.getNo());
			
			//5. SQL 실행
			rs = pstmt.executeQuery();
			
			//6. 결과처리
			while(rs.next()) {
				Long no = rs.getLong(1);
				Long gNo = rs.getLong(2);
				Long oNo = rs.getLong(3);
				Long depth = rs.getLong(4);
				
				vo.setNo(no);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
	
			}
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
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
		
		return vo;		
	}
	
	
	public boolean deleteBoard(BoardVo vo) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = new MyConnection().getConnection();
			
			String sql ="delete from board where no=?";
			pstmt = connection.prepareStatement(sql);

			pstmt.setLong(1, vo.getNo());
			
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
	
	public boolean hitUpdate(BoardVo vo) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = new MyConnection().getConnection();
			
			String sql ="update board set hit=?+1 where no=?";
			pstmt = connection.prepareStatement(sql);

			pstmt.setLong(1, vo.getHit());
			pstmt.setLong(2, vo.getNo());
			
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
	
	public boolean contentsModify(BoardVo vo) {
		boolean result = false;
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = new MyConnection().getConnection();
			
			String sql ="update board set title=?, contents=? where no=?";
			pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());
			
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

	public BoardVo findBoardCount(BoardVo vo,String kwd) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = new MyConnection().getConnection();

			//3. SQL 준비
			String sql ="select count(*) from (select * from board where title like ? or contents like ?) a ;";
			pstmt = connection.prepareStatement(sql);

			//4. Parameter Mapping
			pstmt.setString(1, "%"+kwd+"%");
			pstmt.setString(2, "%"+kwd+"%");
			//5. SQL 실행
			rs = pstmt.executeQuery();

			//6. 결과처리
			while(rs.next()) {
				Long boardCount = rs.getLong(1);
				vo.setBoardCount(boardCount);

			}
		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
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

		return vo;
	}

}
