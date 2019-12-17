package com.majingji.cms.service;

import java.util.List;

import com.majingji.cms.domain.Category;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月15日 下午8:14:51 
* 类功能说明 
*/
public interface CategoryService {

	List<Category> categorys(Integer channelId);

}
