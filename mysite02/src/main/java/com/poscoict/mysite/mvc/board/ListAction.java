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
import com.poscoict.web.util.MvcUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<BoardVo> list = new ArrayList<>();
		BoardDao dao = new BoardDao();
		
		if (request.getParameter("kwd") == null || request.getParameter("kwd").equals("")) {
			list = dao.find("all", null, null);
			request.setAttribute("list", list);
			
		} else {	
			String kwd = request.getParameter("kwd");
			list = dao.find("search", null, kwd);
			request.setAttribute("list", list);
		}
		
		MvcUtil.forward("board/list", request, response);	
	}

}
