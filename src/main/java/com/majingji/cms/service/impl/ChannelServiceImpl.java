package com.majingji.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.majingji.cms.domain.Channel;
import com.majingji.cms.mapper.ChannelMapper;
import com.majingji.cms.service.ChannelService;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月15日 下午8:04:24 
* 类功能说明 
*/
@Service
public class ChannelServiceImpl implements ChannelService {
	@Resource
	private ChannelMapper channelMapper;

	@Override
	public List<Channel> selects() {
		return channelMapper.selects();
	}
	
}
