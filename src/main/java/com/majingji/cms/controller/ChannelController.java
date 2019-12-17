package com.majingji.cms.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.majingji.cms.domain.Channel;
import com.majingji.cms.service.ChannelService;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月15日 下午7:55:24 
* 类功能说明 
*/
@RequestMapping("channel")
@Controller
public class ChannelController {
	@Resource
	private ChannelService channelService;
	@RequestMapping("selects")
	@ResponseBody
	public List<Channel> channels(){
		List<Channel> list = channelService.selects();
		return list;
		
	}
	
}
