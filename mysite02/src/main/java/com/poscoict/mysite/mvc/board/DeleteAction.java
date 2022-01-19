package com.poscoict.mysite.mvc.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		/* 접근 제어 */
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if (authUser == null) {
			MvcUtil.redirect(request.getContextPath() + "/board", request, response);
			return ;
		} 
		
		Long no = Long.parseLong(request.getParameter("no"));
		BoardVo boardWriteUser = new BoardVo();
		BoardDao dao = new BoardDao();
		
		boardWriteUser = dao.find("one", no, null, null).get(0);
		
		if (authUser.getNo() == boardWriteUser.getUserNo()) {
			dao.delete(no);
		}
		
		MvcUtil.redirect(request.getContextPath() + "/board", request, response);
		
	}

}
