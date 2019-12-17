package com.majingji.cms.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月19日 下午8:51:04 
* 类功能说明 
*/
public class AdminInterceptor extends HandlerInterceptorAdapter{
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//false表示没有session就返回null,true表示没有session就新建一个sesssion,默认是true
		HttpSession session = request.getSession(false);
			if(session!=null) {
				if(null!=session.getAttribute("admin")) {
					return true;
				}
			}
			request.setAttribute("message", "请先登录");
			//转发到登录页面
			request.getRequestDispatcher("/WEB-INF/views/passport/login.jsp").forward(request, response);
		return false;
	}

}
