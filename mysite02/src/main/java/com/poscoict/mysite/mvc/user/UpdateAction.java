package com.poscoict.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.poscoict.mysite.dao.UserDao;
import com.poscoict.mysite.vo.UserVo;
import com.poscoict.web.mvc.Action;
import com.poscoict.web.util.MvcUtil;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 접근 제어 */
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if (authUser == null) {
			MvcUtil.redirect(request.getContextPath() + "/user?a=loginform", request, response);
			return ;
		}
		
		Long no = Long.parseLong(request.getParameter("no"));
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");
		
		boolean passwordIsNull = false;
		if (password == null || password.equals("")) {
			passwordIsNull = true;
		}
		
		UserVo userVo = new UserVo();
		userVo.setNo(no);
		userVo.setName(name);
		userVo.setPassword(password);
		userVo.setGender(gender);
		
		new UserDao().update(userVo, passwordIsNull);
		MvcUtil.redirect(request.getContextPath() + "/user?a=logout", request, response);
		
	}

}
