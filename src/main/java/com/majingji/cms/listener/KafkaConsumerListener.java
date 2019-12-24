package com.majingji.cms.listener;

import javax.annotation.Resource;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.majingji.cms.domain.ArticleWithBLOBs;
import com.majingji.cms.service.ArticleService;

/** 
* @author 作者:majingji
* @version 创建时间：2019年12月18日 下午8:54:17 
* 类功能说明 
*/
@Component
public class KafkaConsumerListener implements MessageListener<String, String>{
	@Resource
	private ArticleService articleService;
	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		String key = data.key();
		if(key !=null && key.equals("article_add")) {
			String value = data.value();
			//将字符串转换成对象
			ArticleWithBLOBs article = JSON.parseObject(value, ArticleWithBLOBs.class);
			articleService.insertSelective(article);
		}
		
	}

}
