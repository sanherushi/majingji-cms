package com.majingji.cms.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.majingji.cms.domain.Article;
import com.majingji.cms.domain.ArticleWithBLOBs;
import com.majingji.cms.domain.Links;
import com.majingji.cms.domain.User;
import com.majingji.cms.service.ArticleService;
import com.majingji.cms.service.LinksService;
import com.majingji.cms.service.UserService;
import com.majingji.cms.utils.Result;
import com.majingji.cms.utils.ResultUtil;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月13日 下午8:13:03 
* 类功能说明 
*/
@RequestMapping("admin")
@Controller
public class AdmainController {
	@Resource
	private UserService userService;
	@Resource
	private ArticleService articleService;
	@Resource
	private LinksService linksService;
	@RequestMapping("index")
	public String index() {
		return "admin/index";
	}
	/**
	 * UserController的方法
	 * @param model
	 * @param user
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("user/selects")
	public String list(Model model,User user,@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "3")Integer pageSize) {
		PageInfo<User> page = userService.selects(user, pageNum, pageSize);
		model.addAttribute("page", page);
		model.addAttribute("user", user);
		int[] nums = page.getNavigatepageNums();
		model.addAttribute("nums", nums);
		return "admin/user/users";
	}
	@RequestMapping("user/update")
	@ResponseBody
	public Result<User> update(User user) {
		userService.update(user);//执行修改
		return ResultUtil.success();
	}
	
	
	/**
	 * ArticleController方法
	 * @param article
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("article/articles")
	public String articles(Article article,Model model,@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "3")Integer pageSize) {
		PageInfo<Article> page = articleService.selects(article,pageNum,pageSize);
		int[] nums = page.getNavigatepageNums();
		model.addAttribute("page",page);
		model.addAttribute("article", article);//用于查询条件的回显,便于管理员进行操作
		model.addAttribute("nums", nums);
		return "admin/article/articles";
	}
	
	@RequestMapping("article/update")
	@ResponseBody
	public Result<Article> update(ArticleWithBLOBs article) {
		System.out.println(article.getHot()+"----"+article.getId());
		articleService.update(article);
		return ResultUtil.success();//操作成功时返回的信息
	}
	@RequestMapping("article/article")
	public String detail(Article a,Model model,Integer pageNum) {
		ArticleWithBLOBs article = articleService.selectByPrimaryKey(a.getId());
		model.addAttribute("article", article);
		//将原来的状态值封装到model中
		model.addAttribute("a", a);
		model.addAttribute("pageNum", pageNum);
		return "admin/article/article";
		
	}
	
	@RequestMapping("links/links")
	public String links(Model model,@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "3")Integer pageSize) {
		PageInfo<Links> page = linksService.selects(pageNum,pageSize);
		model.addAttribute("page", page);
		model.addAttribute("nums", page.getNavigatepageNums());
		return "admin/links/links";
	}
	
	@GetMapping("links/add")
	public String toAdd() {
		return "admin/links/add";
	}
	@PostMapping("links/add")
	@ResponseBody
	public Result<Links> add(Links links) {
		linksService.insert(links);
		return ResultUtil.success();
	}
	@PostMapping("links/delLinks")
	@ResponseBody
	public Result<Links> deleteLinksbyId(Integer id){
		linksService.deleteLinksbyId(id);
		return ResultUtil.success();
		
	}
}	
