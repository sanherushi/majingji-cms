package com.majingji.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.majingji.cms.domain.Article;
import com.majingji.cms.domain.ArticleWithBLOBs;
import com.majingji.cms.mapper.ArticleMapper;
import com.majingji.cms.service.ArticleService;
import com.majingji.cms.utils.CMSAjaxException;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月14日 下午5:02:17 
* 类功能说明 
*/
@Service
public class ArticleServiceImpl implements ArticleService {
	@Resource
	private ArticleMapper articleMapper;

	@Override
	public PageInfo<Article> selects(Article article, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Article> list = articleMapper.selects(article);
		PageInfo<Article> page = new PageInfo<Article>(list);
		return page;
	}

	@Override
	public boolean update(ArticleWithBLOBs article) {
		try {
			return articleMapper.updateByPrimaryKeySelective(article)>0;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CMSAjaxException("操作失败", 1);//抛出ajax异常
		}
		
	}

	@Override
	public ArticleWithBLOBs selectByPrimaryKey(Integer id) {
		return articleMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean insertSelective(ArticleWithBLOBs article) {
		try {
			return articleMapper.insertSelective(article)>0;
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException("发表失败");
			throw new CMSAjaxException("发布失败", 1);
		}
	}

	

}
