package com.poscoict.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poscoict.mysite.repository.BoardRepository;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.PagingVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	public Boolean addContents(BoardVo vo) {
		if (vo.getGroupNo() != null) {
			increaseGroupOrderNo(vo);
		}
		
		return boardRepository.insert(vo);
	}
	
	// 글 보기
	public BoardVo getContents(Long no) {
		return boardRepository.findByNo(no);
	}
	
	// 글 수정 하기 전
	public BoardVo getContents(Long no, Long userNo) {
		return boardRepository.findByNo(no);
	}
	
	// 글 수정
	public Boolean updateContents(BoardVo vo) {
		return boardRepository.update("notReply", vo);
	}
	
	// 글 삭제
	public Boolean deleteContents(Long no, Long userNo) {
		return boardRepository.delete(no, userNo);
	}
	
	// 글 리스트 (찾기 결과)
	public Map<String, Object> getContentsList(int currentPage, String keyword) {
		Map<String, Object> map = new HashMap<>();
		
		List<BoardVo> list = boardRepository.find(currentPage, keyword);
		PagingVo pagingVo = boardRepository.Pagination(currentPage, keyword);
		
		map.put("list", list);
		map.put("pagingVo", pagingVo);
		return map;
	}
	
	
	private Boolean increaseGroupOrderNo(BoardVo vo) {
		return boardRepository.update("reply", vo);
	}
}
