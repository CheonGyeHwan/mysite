package com.poscoict.mysite.mvc.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.BoardDao;
import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class HitAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean readFirst = true;
		Long no = Long.parseLong(request.getParameter("no"));
		BoardVo vo = null;
		BoardDao dao = new BoardDao();
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		String userNo = Long.toString(authUser == null? 0: authUser.getNo());
		String boardNo = request.getParameter("no");
		
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			Cookie cookie = new Cookie(boardNo, userNo);
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(24*60*60);
			response.addCookie(cookie);
			
			vo = new BoardVo();
			vo.setNo(no);
			dao.update("hit", vo);
		} else if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (boardNo.equals(cookie.getName()) && userNo.equals(cookie.getValue())) {
					readFirst = false;
					break;			
				}
			}
			
			if (readFirst) {
				Cookie cookie = new Cookie(boardNo, userNo);
				cookie.setPath(request.getContextPath());
				cookie.setMaxAge(2*60); // 1 day
				response.addCookie(cookie);
				
				vo = new BoardVo();
				vo.setNo(no);
				dao.update("hit", vo);
			}	
		}
		
		MvcUtil.redirect(request.getContextPath() + "/board?a=view&no=" + no, request, response);
	}

}
