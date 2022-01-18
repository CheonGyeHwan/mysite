package com.poscoict.mysite.mvc.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = Long.parseLong(request.getParameter("no"));
		
		BoardVo boardView = new BoardVo();
		BoardDao dao = new BoardDao();
		
		boardView = dao.find("view", no, null).get(0);
		request.setAttribute("boardView", boardView);
		MvcUtil.forward("board/view", request, response);
	}

}
