package com.majingji.cms.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.majingji.cms.domain.Article;
import com.majingji.cms.domain.Collect;
import com.majingji.cms.domain.User;
import com.majingji.cms.mapper.CollectionMapper;
import com.majingji.cms.service.CollectService;
import com.majingji.cms.utils.CMSAjaxException;
import com.majingji.utils.StringUtil;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月24日 上午9:51:03 
* 类功能说明 
*/
@Service
public class CollectServiceImpl implements CollectService {
	@Resource
	private CollectionMapper collectionMapper;

	@Override
	public int selectByText(String title, User user) {
		return collectionMapper.selectByText(title,user);
	}

	@Override
	public void insert(Collect collect) {
		if(!StringUtil.isHttpUrl(collect.getUrl())) {
			throw new CMSAjaxException("不是有效的地址", 1);
		}
		try {
			collect.setCreated(new Date());
			collectionMapper.insert(collect);
		} catch (Exception e) {
			throw new CMSAjaxException("操作失败", 1);
		}
	}

	@Override
	public PageInfo<Collect> selects(Integer pageNum, Integer pageSize,User user) {
		PageHelper.startPage(pageNum, pageSize);
		List<Collect> list = collectionMapper.selects(user);
		PageInfo<Collect> page = new PageInfo<Collect>(list);
		return page;
	}

	@Override
	public void deletebyId(Integer id) {
		try {
			collectionMapper.deletebyId(id);
		} catch (Exception e) {
			throw new CMSAjaxException("操作失败", 1);
		}
		
	}

	
}
