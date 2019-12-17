package com.majingji.cms.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.majingji.cms.domain.Links;
import com.majingji.cms.mapper.LinksMapper;
import com.majingji.cms.service.LinksService;
import com.majingji.cms.utils.CMSAjaxException;
import com.majingji.utils.StringUtil;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月23日 下午4:10:29 
* 类功能说明 
*/
@Service
public class LinksServiceImpl implements LinksService {
	@Resource
	private LinksMapper linksMapper;

	@Override
	public PageInfo<Links> selects(Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<Links> list = linksMapper.selects();
		PageInfo<Links> page = new PageInfo<Links>(list);
		return page;
	}

	@Override
	public void insert(Links links) {
		System.out.println(links.getUrl());
		if(!StringUtil.isHttpUrl(links.getUrl())) {
			throw new CMSAjaxException("链接路径错误", 1);
		}
		try {
			//创建时间
			links.setCreated(new Date());
			linksMapper.insert(links);
		} catch (Exception e) {
			throw new CMSAjaxException("添加失败", 1);
		}
		
	}

	@Override
	public void deleteLinksbyId(Integer id) {
		try {
			linksMapper.deleteLinksbyId(id);
		} catch (Exception e) {
			throw new CMSAjaxException("操作失败",1);
		}
		
	}

}
