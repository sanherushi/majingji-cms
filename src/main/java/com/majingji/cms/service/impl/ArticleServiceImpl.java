package com.majingji.cms.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.majingji.cms.domain.Article;
import com.majingji.cms.domain.ArticleWithBLOBs;
import com.majingji.cms.domain.Category;
import com.majingji.cms.domain.Channel;
import com.majingji.cms.domain.User;
import com.majingji.cms.mapper.ArticleMapper;
import com.majingji.cms.service.ArticleService;
import com.majingji.cms.utils.CMSAjaxException;
import com.majingji.cms.utils.ESUtils;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月14日 下午5:02:17 
* 类功能说明 
 *
*/
@Service
public class ArticleServiceImpl implements ArticleService {
	@Resource
	private ArticleMapper articleMapper;
	@Resource
	private RedisTemplate<String, Article> redisTemplate;
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
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
			int result = articleMapper.updateByPrimaryKeySelective(article);
			if(result>0) {
				//因为修改成功后最新的文章可能会发生变化,所以要把redis中的数据删除
				redisTemplate.delete("last_article");
				redisTemplate.delete("hot_article");
			}
			if(article.getDeleted()!=null && article.getDeleted()==1) {
				//将elasticsearch中对应的数据删除
				elasticsearchTemplate.delete(Article.class, article.getId().toString());
			}else {
				if(article.getStatus()!=null && article.getStatus()==1) {
					//如果审核通过,存入es中,如果数据已存在会更新
					IndexQuery query = new IndexQuery();
					//因为是Article而不是其子类类型,所以需要根据子类中id的值，获取到Article对象的数据
					Article art = articleMapper.selectByPrimaryKey(article.getId());
					query.setObject(art);
					elasticsearchTemplate.index(query);
				}
			}
			return result>0;
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
	
	/**
	 * 查询最新的文章
	 * 第一次访问的时候，redis中没有数据，从mysql数据库中获取数据
	 * 怎么判断是第一次访问?
	 * 直接查看redis中有没有对应的数据，如果没有，则为第一次访问
	 * 之后再次访问时，直接从redis中获取数据
	 */
	@Override
	public PageInfo<Article> selectLast(Article lastArticle, Integer pageNum, Integer pageSize) {
		//先判断redis有没有对应的数据
		ListOperations<String, Article> opsForList = redisTemplate.opsForList();
		List<Article> articles = null;
		if(redisTemplate.hasKey("last_article")) {
			articles = opsForList.range("last_article", 0, -1);
		}else {
			//从数据库中查询
			PageHelper.startPage(pageNum, pageSize);
			articles = articleMapper.selects(lastArticle);
			//将数据存入redis中
			opsForList.rightPushAll("last_article", articles);
		}
		return new PageInfo<Article>(articles);
	}
	
	/**
	 * 热门文章
	 * @param article
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Article> selectHot(Article article, Integer pageNum, Integer pageSize) {
		//先判断redis中有没有对应的数据
		ListOperations<String, Article> opsForList = redisTemplate.opsForList();
		PageInfo<Article> pageInfo = null;
		if(redisTemplate.hasKey("hot_article")) {
			List<Article> list = opsForList.range("hot_article", (pageNum-1)*pageSize, pageNum*pageSize-1);
			Page page = new Page<Article>(pageNum,pageSize);
			//获取总记录条数
			Long size = opsForList.size("hot_article");
			page.setTotal(size);
			page.addAll(list);
			pageInfo = new PageInfo<Article>(page,3);
//			page = new PageInfo<Article>(list);
//			//设置上一页
//			page.setPrePage(pageNum==1?1:pageNum-1);
//			//设置当前页
//			page.setPageNum(pageNum);
//			//总条数
//			Long size = opsForList.size("hot_article");
//			//设置总页数
//			page.setPages((int) ((size+pageSize-1)/pageSize));
//			//设置下一页
//			page.setNextPage(pageNum==page.getPages()?page.getPages():pageNum+1);
		}else {
			//先查询出所有的热门文章
			List<Article> selects = articleMapper.selects(article);
			//将热门文章存入redis中
			opsForList.rightPushAll("hot_article", selects);
			PageHelper.startPage(pageNum, pageSize);
			List<Article> selects2 = articleMapper.selects(article);
			pageInfo = new PageInfo<Article>(selects2);
		}
		return pageInfo;
		
	}
	
	/**
	 * 高亮查询
	 */
	@Override
	public PageInfo<Article> selectES(Integer pageNum, Integer pageSize, String key) {
		//实体类中成员变量如果是实体类类型,则将其类对象,存入clazzs中
		Class [] classzz = new Class[] {User.class,Channel.class,Category.class};
		AggregatedPage<Article> selectObjects = ESUtils.selectObjects(elasticsearchTemplate, Article.class, Arrays.asList(classzz), pageNum-1, pageSize, "id", new String [] {"title"}, key);
		List<Article> content = selectObjects.getContent();
		//创建Page对象
		Page<Article> page = new Page<Article>(pageNum, pageSize);
		//设置总条数
		page.setTotal(selectObjects.getTotalElements());
		page.addAll(content);
		PageInfo<Article> pageInfo = new PageInfo<Article>(page,3);
		return pageInfo;
	}

}
