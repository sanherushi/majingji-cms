package com.majingji.cms.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.majingji.cms.domain.Category;
import com.majingji.cms.service.CategoryService;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月15日 下午8:14:02 
* 类功能说明 
*/
@RequestMapping("category")
@Controller
public class CategoryController {
	@Resource
	private CategoryService categoryService;
	@RequestMapping("selects")
	@ResponseBody
	public List<Category> categorys(Integer channelId) {
		List<Category> list = categoryService.categorys(channelId);
		return list;
		
	}

}
