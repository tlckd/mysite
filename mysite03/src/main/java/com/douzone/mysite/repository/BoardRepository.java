package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.GuestBookVo;

@Repository
public class BoardRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(BoardVo vo) {
		return sqlSession.insert("board.insert",vo);
	}
	
	public int insertReply(BoardVo vo) {
		return sqlSession.insert("board.insertReply",vo);		
	}
	
	
	
	
	public List<BoardVo> findAll(BoardVo vo) {
		return sqlSession.selectList("board.findAll",vo);
	}
	
	
	public BoardVo findViewContents(BoardVo vo) {
		return sqlSession.selectOne("board.findViewContents",vo);
	}
	
	public BoardVo findCurrentG_noO_noDepth(BoardVo vo) {
		return sqlSession.selectOne("board.findCurrentG_noO_noDepth",vo);
	}
	
	public Long findBoardCount(BoardVo vo) {
		return sqlSession.selectOne("board.findBoardCount",vo);	
	}
	
	
	
	
	
	public int deleteBoard(BoardVo vo) {
		return sqlSession.delete("board.deleteBoard",vo);
	}
	
	
	
	
	public int hitUpdate(BoardVo vo) {
		return sqlSession.update("board.hitUpdate",vo);	
	}
	
	public int contentsModify(BoardVo vo) {
		return sqlSession.update("board.contentsModify",vo);	
	}
	
	public int insertReplyUpdate(BoardVo vo) {
		return sqlSession.update("board.insertReplyUpdate",vo);
	}
	

}
