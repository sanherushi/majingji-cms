package com.majingji.cms.service;

import com.github.pagehelper.PageInfo;
import com.majingji.cms.domain.Comment;
import com.majingji.cms.domain.User;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月24日 下午4:00:52 
* 类功能说明 
*/
public interface CommentService {

	void insert(Comment comment);

	PageInfo<Comment> selects(Integer id, Integer pageNum, Integer pageSize);

	PageInfo<Comment> selectbyId(User user, Integer pageNum, Integer pageSize);

	void deleteComment(Integer id);
	
}
