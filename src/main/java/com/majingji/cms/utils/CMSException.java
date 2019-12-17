package com.majingji.cms.utils;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月18日 下午4:59:56 
* 类功能说明 
*/
public class CMSException extends RuntimeException{
	private String message;

	public CMSException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CMSException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
	
	

}
