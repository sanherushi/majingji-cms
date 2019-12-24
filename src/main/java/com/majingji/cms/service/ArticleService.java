package com.majingji.cms.service;


import com.github.pagehelper.PageInfo;
import com.majingji.cms.domain.Article;
import com.majingji.cms.domain.ArticleWithBLOBs;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月14日 下午5:02:01 
* 类功能说明 
*/
public interface ArticleService {
	
	PageInfo<Article> selects(Article article, Integer pageNum, Integer pageSize);

	boolean update(ArticleWithBLOBs article);
	
	ArticleWithBLOBs selectByPrimaryKey(Integer id);

	boolean insertSelective(ArticleWithBLOBs article);

	PageInfo<Article> selectLast(Article lastArticle, Integer pageNum, Integer pageSize);

	PageInfo<Article> selectHot(Article hot, Integer pageNum, Integer pageSize);

	PageInfo<Article> selectES(Integer pageNum, Integer pageSize, String key);

}
