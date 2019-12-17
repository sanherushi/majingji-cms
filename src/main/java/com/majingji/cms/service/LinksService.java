package com.majingji.cms.service;

import com.github.pagehelper.PageInfo;
import com.majingji.cms.domain.Links;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月23日 下午4:10:10 
* 类功能说明 
*/
public interface LinksService {

	PageInfo<Links> selects(Integer pageNum, Integer pageSize);

	void insert(Links links);

	void deleteLinksbyId(Integer id);

}
