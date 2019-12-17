package com.majingji.cms.utils;
/** 
* @author 作者:majingji
* @version 创建时间：2019年11月21日 下午7:36:20 
* 类功能说明 
*/
public class CMSAjaxException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private String message;
	private Integer code;
	public CMSAjaxException() {
		super();
	}
	public CMSAjaxException(String message, Integer code) {
		super();
		this.message = message;
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	
	

}
