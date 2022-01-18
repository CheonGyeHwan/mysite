package com.poscoict.mysite.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.poscoict.mysite.dao.GuestbookDao;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int no = Integer.parseInt(request.getParameter("no"));
		String password = request.getParameter("password");
		
		GuestbookDao dao = new GuestbookDao();
		dao.delete(no, password);
		
		MvcUtil.redirect(request.getContextPath() + "/guestbook", request, response);
	}

}
