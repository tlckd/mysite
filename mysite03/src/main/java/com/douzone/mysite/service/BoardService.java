package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Service
public class BoardService {

	@Autowired
	BoardRepository boardRepository;

	public List<BoardVo> findAll(BoardVo vo) {
		return boardRepository.findAll(vo);
	}
	
	public Long findBoardCount(BoardVo vo) {
		return boardRepository.findBoardCount(vo);
	}
	
	public BoardVo findViewContents(BoardVo vo) {
		return boardRepository.findViewContents(vo);
	}
	
	public int hitUpdate(BoardVo vo) {
		return boardRepository.hitUpdate(vo);
	}
	

	public void insert(BoardVo vo, Long boardno, Long authNo) {
		
		if(authNo != null) {
			vo.setUserNo(authNo);
		}
		
		if("".equals(boardno) || boardno==null) {
			boardRepository.insert(vo);
		}
		
		vo.setNo(boardno);
		vo = boardRepository.findCurrentG_noO_noDepth(vo);
		boardRepository.insertReplyUpdate(vo);
		boardRepository.insertReply(vo);
	
	}
	
}
