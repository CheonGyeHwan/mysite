package com.poscoict.mysite.interceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.poscoict.mysite.repository.SiteRepository;

public class SiteInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private SiteRepository siteRepository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		ServletContext context = request.getServletContext();
		
		if (context.getAttribute("site") == null ) {
			context.setAttribute("site", siteRepository.select());
			return true;
		}
		
		return true;
	}

}
