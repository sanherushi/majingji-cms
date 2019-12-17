package com.majingji.cms.utils;
/** 
* @author 作者:majingji
* @version 创建时间：2019年11月21日 下午7:21:52 
* 类功能说明:统一返回结果 
* 
*/
public class Result<T> {
	//消息码
	private Integer code;
	//提示信息
	private String msg;
	//具体的内容
	private T data;
	public Result() {
		super();
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	
}
