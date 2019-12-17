package com.majingji.cms.utils;

/**
 * @author 作者:majingji
 * @version 创建时间：2019年11月22日 下午1:33:32 类功能说明
 */
public enum ArticleEnum {
//	HTML(0, "html"), IMAGE(1, "image");
//
//	ArticleEnum(Integer code, String name) {
//		this.code = code;
//		this.name = name;
//	}
//
//	private Integer code;
//	private String name;
//
//	public Integer getCode() {
//		return code;
//	}
//
//	public void setCode(Integer code) {
//		this.code = code;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public static void main(String[] args) {
//		System.out.println(HTML.getCode());
//		System.out.println(HTML.getName());
//		// IMAGE.getCode();
//	}
	
	
	HTML(0,"html"),IMAGE(1,"image");
	
	private Integer code;
	private String name;
	private ArticleEnum(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public static void main(String[] args) {
		System.out.println(HTML.getCode());
		System.out.println(HTML.getName());
	}

}
