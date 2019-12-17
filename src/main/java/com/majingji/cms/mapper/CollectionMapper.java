package com.majingji.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.majingji.cms.domain.Article;
import com.majingji.cms.domain.Collect;
import com.majingji.cms.domain.User;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月24日 上午9:51:48 
* 类功能说明 
*/
public interface CollectionMapper {
	
	int selectByText(@Param("title")String title, @Param("user")User user);
	
	void insert(Collect collect);

	List<Collect> selects(User user);

	void deletebyId(@Param("id")Integer id);

}
