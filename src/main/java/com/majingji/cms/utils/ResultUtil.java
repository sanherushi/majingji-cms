package com.majingji.cms.utils;
/** 
* @author 作者:majingji
* @version 创建时间：2019年11月21日 下午7:25:05 
* 类功能说明 
*/
public class ResultUtil {
	//操作成功
	public static Result success(Object object) {
		Result result = new Result();
		result.setCode(0);
		result.setMsg("成功");
		result.setData(object);
		return result;
	}
	//没有具体的内容
	public static Result success() {
		return success(null);
	}
	//操作异常
	public static Result error(Integer code,String msg) {
		Result result = new Result();
		result.setCode(code);
		result.setMsg(msg);
		return result;
		
	}
}
