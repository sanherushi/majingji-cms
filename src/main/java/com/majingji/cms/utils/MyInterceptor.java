package com.majingji.cms.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author 作者:majingji
 * @version 创建时间：2019年11月20日 上午11:34:36 类功能说明
 */
public class MyInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(false);
		if (null != session) {
			if (null != session.getAttribute("user")) {
				return true;
			}
		}
		request.setAttribute("message", "请先登录");
		request.getRequestDispatcher("/WEB-INF/views/passport/login.jsp").forward(request, response);
		return false;

	}
}
