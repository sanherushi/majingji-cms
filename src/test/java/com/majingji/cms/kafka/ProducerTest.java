package com.majingji.cms.kafka;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.majingji.cms.domain.ArticleWithBLOBs;
import com.majingji.cms.domain.Category;
import com.majingji.cms.domain.Channel;
import com.majingji.cms.service.CategoryService;
import com.majingji.cms.service.ChannelService;
import com.majingji.utils.DateUtil;
import com.majingji.utils.RandomUtil;
import com.majingji.utils.StreamUtil;

/** 
* @author 作者:majingji
* @version 创建时间：2019年12月18日 下午7:43:55 
* 类功能说明 
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-beans.xml")
public class ProducerTest {
	@Resource
	private KafkaTemplate<String, String> kafkaTemplate;
	@Resource
	private ChannelService channelService;
	@Resource
	private CategoryService categoryService;
	@Test
	public void testSendMsg() throws FileNotFoundException, Exception {
		File dir = new File("D:\\1707EJsoup");
		//返回某个目录下所有文件和目录的绝对路径，返回类型File[]
		File[] listFiles = dir.listFiles();
		for (File file : listFiles) {
			//读取每一个文件的内容,获取文章内容
			String content = StreamUtil.readTextFile(file);
			//获取文章的标题
			String title = file.getName().replace(".txt", "");
			ArticleWithBLOBs article = new ArticleWithBLOBs();
			//设置文章的内容
			article.setContent(content);
			//设置文章的标题
			article.setTitle(title);
			//在文本内容中截取前140个字作为摘要
			if(content.length()>140) {
				article.setSummary(content.substring(0, 140));
			}
			//“点击量”和“是否热门”、“频道”字段要使用随机值
			//点击量在0到10000之间随机
			article.setHot(RandomUtil.random(0, 10000));
			//设置是否热门
			article.setHot(RandomUtil.random(0, 1));
			//查询所有的栏目
			List<Channel> channels = channelService.selects();
			Channel channel = channels.get(RandomUtil.random(0, channels.size()-1));
			//设置文章的栏目id
			article.setChannelId(channel.getId());
			//查询特定栏目下的所有的分类
			List<Category> categories = categoryService.categorys(article.getChannelId());
			if(null !=categories) {
				Category category = categories.get(RandomUtil.random(0, categories.size()-1));
				//设置文章的分类id
				article.setCategoryId(category.getId());
			}
			//文章发布日期从2019年1月1日模拟到今天
			article.setCreated(DateUtil.randomDate("2019-1-1", "2019-12-18"));
			//其它的字段随便模拟
			//设置状态
			article.setStatus(0);
			//设置是否删除
			article.setDeleted(0);
			//设置文章类型
			article.setContentType(0);
			System.out.println(article);
			//将article转换成json类型的字符串
			String jsonString = JSON.toJSONString(article);
			//发送到kafka
			kafkaTemplate.sendDefault("article_add", jsonString);
		}
	}
}
