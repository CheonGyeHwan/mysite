package com.poscoict.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = Long.parseLong(request.getParameter("no"));
		
		BoardVo boardModify = new BoardVo();
		BoardDao dao = new BoardDao();
		
		boardModify = dao.find("one", no, null, null).get(0);
		request.setAttribute("boardModify", boardModify);
		MvcUtil.forward("board/modify", request, response);
	}

}
