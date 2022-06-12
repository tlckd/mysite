package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Service
public class BoardService {

	@Autowired
	BoardRepository boardRepository;
	
	
	private static final int LIST_SIZE = 5; // 리스팅되는 게시물의 수
	private static final int PAGE_SIZE = 5; // 페이지 리스트의 페이지 수
	
	
	public Map<String, Object> getContentsList(Long currentPage, String keyword) {
		
		// 1. 페이징을 위한 기본 데이터 계산
		long totalCount = boardRepository.totalCount(keyword);
		long pageCount = (int) Math.ceil((double) totalCount / LIST_SIZE);
		long blockCount = (int) Math.ceil((double) pageCount / PAGE_SIZE);
		long currentBlock = (int) Math.ceil((double) currentPage / PAGE_SIZE);

		// 2. 파라미터 page 값 검증
		if (currentPage > pageCount) {
			currentPage = pageCount;
			currentBlock = (int) Math.ceil((double) currentPage / PAGE_SIZE);
		}

		if (currentPage < 1) {
			currentPage = (long) 1;
			currentBlock = 1;
		}

		// 3. view에서 페이지 리스트를 렌더링 하기위한 데이터 값 계산
		long beginPage = currentBlock == 0 ? 1 : (currentBlock - 1) * PAGE_SIZE + 1;
		long prevPage = (currentBlock > 1) ? (currentBlock - 1) * PAGE_SIZE : 0;
		long nextPage = (currentBlock < blockCount) ? currentBlock * PAGE_SIZE + 1 : 0;
		long endPage = (nextPage > 0) ? (beginPage - 1) + LIST_SIZE : pageCount;

		// 4. 리스트 가져오기
		List<BoardVo> list = boardRepository.findAllByPageAndKeword(keyword, currentPage, LIST_SIZE);

		// 5. 리스트 정보를 맵에 저장
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("list", list);
		map.put("totalCount", totalCount);
		map.put("listSize", LIST_SIZE);
		map.put("currentPage", currentPage);
		map.put("beginPage", beginPage);
		map.put("endPage", endPage);
		map.put("prevPage", prevPage);
		map.put("nextPage", nextPage);
		map.put("keyword", keyword);

		return map;
		
	}
	

	public BoardVo findViewContents(BoardVo vo) {
		return boardRepository.findViewContents(vo);
	}
	
	public int hitUpdate(BoardVo vo) {
		return boardRepository.hitUpdate(vo);
	}
	
	
	
	
	public void insert(BoardVo vo) {
			boardRepository.insert(vo);	
	}
	
	
	public void replyInsert(BoardVo vo, String title, String contents, long uno) {
			vo = boardRepository.findCurrentG_noO_noDepth(vo);
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setUserNo(uno);
			
			boardRepository.insertReplyUpdate(vo);
			boardRepository.insertReply(vo);
		}


	public void deleteContents(Long boardNo) {
		boardRepository.deleteBoard(boardNo);
		
	}


	public void modifyContents(BoardVo boardVo) {
		boardRepository.contentsModify(boardVo);
		
	}

}
