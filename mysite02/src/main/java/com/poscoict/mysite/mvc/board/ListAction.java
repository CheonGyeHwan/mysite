package com.poscoict.mysite.mvc.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.paging.Criteria;
import com.poscoict.web.paging.PageMakerDto;
import com.poscoict.web.util.MvcUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<BoardVo> list = new ArrayList<>();
		BoardDao dao = new BoardDao();

		int pageNum = (request.getParameter("pageNum") == null? 1 : Integer.parseInt(request.getParameter("pageNum")));
		Criteria cri = new Criteria(pageNum);
		PageMakerDto pageMakerDto = null;
			
		if (request.getParameter("kwd") == null || request.getParameter("kwd").equals("")) {
			int total = dao.find("all", null, null, null).size();
			
			list = dao.find("all", null, null, cri);
			pageMakerDto = new PageMakerDto(cri, total);
			
			request.setAttribute("list", list);
			request.setAttribute("pageMakerDto", pageMakerDto);
			
		} else {
			String kwd = request.getParameter("kwd");
			int total = dao.find("search", null, kwd, null).size();
			
			list = dao.find("search", null, kwd, cri);
			pageMakerDto = new PageMakerDto(cri, total);
			pageMakerDto.setSearchKeword(kwd);
			
			request.setAttribute("list", list);
			request.setAttribute("pageMakerDto", pageMakerDto);
		}
		
		MvcUtil.forward("board/list", request, response);	
	}

}
