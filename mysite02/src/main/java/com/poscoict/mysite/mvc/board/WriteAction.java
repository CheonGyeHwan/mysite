package com.poscoict.mysite.mvc.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		Long userNo = Long.parseLong(request.getParameter("userNo"));
		BoardDao dao = new BoardDao();
		BoardVo vo = new BoardVo();
		
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setUserNo(userNo);
		
		if (request.getParameter("groupNo") == null) {
			dao.insert("new", vo);
		} else {
			int groupNo = Integer.parseInt(request.getParameter("groupNo"));
			int orderNo = Integer.parseInt(request.getParameter("orderNo"));
			int depth = Integer.parseInt(request.getParameter("depth"));
			
			vo.setGroupNo(groupNo);
			vo.setOrderNo(orderNo);
			vo.setDepth(depth);
			
			dao.insert("reply", vo);
		}
		
		MvcUtil.redirect(request.getContextPath() + "/board", request, response);
	}

}
