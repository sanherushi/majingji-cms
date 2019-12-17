package com.majingji.cms.vo;

import java.util.Date;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月24日 下午5:03:46 
* 类功能说明 
*/
public class CommentVO {
	private Integer username;
	private String content;
	private Date created;
	public CommentVO() {
		super();
	}
	public Integer getUsername() {
		return username;
	}
	public void setUsername(Integer username) {
		this.username = username;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	

}
