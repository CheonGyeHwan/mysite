package com.poscoict.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.PagingVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	private int boardTotalCount(String keyword) {
		return sqlSession.selectOne("board.boardTotalCount", keyword);
	}
	
	public PagingVo Pagination(int currentPage, String keyword) {
		int total = boardTotalCount(keyword);
		PagingVo pagingVo = new PagingVo(currentPage, total);
		
		if (!(keyword == null || keyword.equals(""))) {
			pagingVo.setKeyword(keyword);
		}
		
		return pagingVo;
	}
	
	public List<BoardVo> find(int currentPage, String keyword) {
		PagingVo pagingVo = Pagination(currentPage, keyword);
		int boardAmount = pagingVo.getBoardAmount();
		int skip = pagingVo.getSkip();
		
		Map<String, Object> map = new HashMap<>();
		map.put("keyword", keyword);
		map.put("boardAmount", boardAmount);
		map.put("skip", skip);

		return sqlSession.selectList("board.find", map);
	}
	
	public BoardVo findByNo(Long boardNo) {
		return sqlSession.selectOne("board.findByNo", boardNo); 
	}	
	
	public boolean insert(BoardVo vo) {
		int count = sqlSession.insert("board.insert", vo);
		return count == 1;
	}
	
	public boolean update(String method, BoardVo vo) {
		Map<String, Object> map = new HashMap<>();
		map.put("method", method);
		map.put("vo", vo);
		
		int count = sqlSession.update("board.update", map);
		return count == 1;
	}
	
	public boolean delete(Long no, Long userNo) {
		BoardVo vo = new BoardVo();
		vo.setNo(no);
		vo.setUserNo(userNo);
		
		int count = sqlSession.delete("board.delete", vo);
		return count == 1;
	}

}