package com.majingji.cms.service;

import com.github.pagehelper.PageInfo;
import com.majingji.cms.domain.Article;
import com.majingji.cms.domain.Collect;
import com.majingji.cms.domain.User;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月24日 上午9:50:35 
* 类功能说明 
*/
public interface CollectService {

	int selectByText(String title, User user);

	void insert(Collect collect);

	PageInfo<Collect> selects(Integer pageNum, Integer pageSize, User user);

	void deletebyId(Integer id);


}
