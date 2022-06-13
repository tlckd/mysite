package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

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
	
	
	
	
	public List<BoardVo> findAllByPageAndKeword(String keyword, Long currentPage, Integer size) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("keyword", keyword);
		map.put("startIndex", (currentPage - 1) * size);
		map.put("size", size);
		
		return sqlSession.selectList("board.findAllByPageAndKeword",map);
	}
	
	
	public BoardVo findViewContents(BoardVo vo) {
		return sqlSession.selectOne("board.findViewContents",vo);
	}
	
	public BoardVo findCurrentG_noO_noDepth(BoardVo vo) {
		return sqlSession.selectOne("board.findCurrentG_noO_noDepth",vo);
	}
	
	public Long totalCount(String keyword) {
		return sqlSession.selectOne("board.totalCount",keyword);	
	}
	

	public int deleteBoard(Long boardNo, Long no) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("boardNo", boardNo);
		map.put("no", no);
		return sqlSession.delete("board.deleteBoard",map);
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
