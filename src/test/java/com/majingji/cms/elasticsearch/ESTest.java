package com.majingji.cms.elasticsearch;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.majingji.cms.domain.Article;
import com.majingji.cms.mapper.ArticleMapper;

/** 
* @author 作者:majingji
* @version 创建时间：2019年12月19日 上午10:16:00 
* 类功能说明 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-beans.xml")
public class ESTest {
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	@Resource
	private ArticleMapper articleMapper;
	/**
	 * 从数据库中查询出具体的文章,存入es中
	 */
	@Test
	public void testToES() {
		Article article = new Article();
		//设置文章状态,1是通过审核
		article.setStatus(1);
		//设置文章是否删除,0是未删除
		article.setDeleted(0);
		List<Article> list = articleMapper.selects(article);
		for (Article article2 : list) {
			IndexQuery query = new IndexQuery();
			query.setId(article2.getId()+"");
			query.setObject(article2);
			elasticsearchTemplate.index(query);
		}
		System.out.println("存储完毕");
	}
}
