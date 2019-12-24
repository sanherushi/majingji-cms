package com.majingji.cms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.majingji.cms.domain.Article;
import com.majingji.cms.domain.ArticleWithBLOBs;
import com.majingji.cms.domain.Category;
import com.majingji.cms.domain.Channel;
import com.majingji.cms.domain.Collect;
import com.majingji.cms.domain.Comment;
import com.majingji.cms.domain.Links;
import com.majingji.cms.domain.User;
import com.majingji.cms.service.ArticleService;
import com.majingji.cms.service.CategoryService;
import com.majingji.cms.service.ChannelService;
import com.majingji.cms.service.CollectService;
import com.majingji.cms.service.CommentService;
import com.majingji.cms.service.LinksService;
import com.majingji.cms.utils.ArticleEnum;
import com.majingji.cms.utils.Result;
import com.majingji.cms.utils.ResultUtil;
import com.majingji.cms.vo.ArticleVO;
import com.majingji.cms.vo.CommentVO;
import com.majingji.utils.StringUtil;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月20日 下午1:40:12 
* 类功能说明 
*/
@Controller
public class IndexController {
	private static final String User = null;
	@Resource
	private ChannelService channelService;//栏目
	@Resource
	private ArticleService articleService;//文章
	@Resource
	private CategoryService categoryService;//分类
	@Resource
	private LinksService linksService;//链接
	@Resource
	private CollectService collectService;
	@Resource
	private CommentService commentService;
	
	@RequestMapping(value = {"","/","index"})
	public String index(Article article,Model model,@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "5")Integer pageSize,String key) {
		long s1 = System.currentTimeMillis();
		System.out.println(article.getChannelId()+"---------------");
		
		article.setStatus(1);//必须是审核通过的文章
		article.setDeleted(0);//必须是没有被删除的文章
		
		//定义几个线程
		Thread t1 = null;
		Thread t2 = null;
		Thread t3 = null;
		Thread t4 = null;
		Thread t5 = null;
		Thread t6 = null;
		
		//t1线程用于显示左侧的栏目
		t1 = new Thread(new Runnable() {
			public void run() {
				//查询出左侧的栏目
				List<Channel> channels = channelService.selects();
				//用于显示左侧的栏目
				model.addAttribute("channels", channels);
			}
		});
		
		//t2线程用于显示热门文章
		t2 = new Thread(new Runnable() {
			public void run() {
				if(key!=null && !key.trim().equals("")) {
					//如果搜索条件不为空，则查询es，进行高亮显示
					PageInfo<Article> info = articleService.selectES(pageNum, pageSize,key);
					System.out.println(info+"------------------------高亮数据");
					model.addAttribute("page", info);
					model.addAttribute("nums", info.getNavigatepageNums());
					model.addAttribute("key", key);
				}else {
					//如果没有栏目,默认选择热门文章
					if(null ==article.getChannelId()) {
						Article hot = new Article();
						hot.setStatus(1);//1表示审核过
						hot.setDeleted(0);//0表示没有被删除
						hot.setHot(1);//1表示是热门文章
						hot.setContentType(ArticleEnum.HTML.getCode());//0表示是文章,1表示是图片集
						//查询出全部的热门文章
						//PageInfo<Article> info = articleService.selects(hot, pageNum, pageSize);
						PageInfo<Article> info = articleService.selectHot(hot,pageNum,pageSize);
						model.addAttribute("nums", info.getNavigatepageNums());
						model.addAttribute("page", info);
					}
				}
				
			}
			
		});
		
		//t3线程获取栏目下的分类
		t3 = new Thread(new Runnable() {
			public void run() {
				if(null !=article.getChannelId()) {
					//通过栏目Id获取栏目下所有的分类
					List<Category> categorys = categoryService.categorys(article.getChannelId());
					model.addAttribute("categorys", categorys);
					//显示分类下的文章
					PageInfo<Article> info = articleService.selects(article, pageNum, pageSize);
					model.addAttribute("nums", info.getNavigatepageNums());
					model.addAttribute("page", info);
				}
			}
		});
		
		//t4线程显示最新的文章
		t4 = new Thread(new Runnable() {
			public void run() {
				//右侧边栏显示最新的5片文章
				Article lastArticle = new Article();
				lastArticle.setStatus(1);
				lastArticle.setDeleted(0);
				lastArticle.setContentType(ArticleEnum.HTML.getCode());//显示文章
				//把当前页和每页的记录数先写死,保证不管怎么分页最新的5篇文章都不会改变
				//PageInfo<Article> lastInfo = articleService.selects(lastArticle, 1, 5);
				PageInfo<Article> lastInfo = articleService.selectLast(lastArticle,1,5);
				model.addAttribute("lastInfo", lastInfo);
				
			}
		});
		
		//t5用于显示图片集
		t5 = new Thread(new Runnable() {
			public void run() {
				Article picArticle = new Article();
				picArticle.setStatus(1);//审核通过
				picArticle.setDeleted(0);//没有被删除
				picArticle.setContentType(ArticleEnum.IMAGE.getCode());//1表示是图片集
				PageInfo<Article> picInfo = articleService.selects(picArticle, 1, 5);//显示最新的5个图片集
				List<Article> list = picInfo.getList();
				for (Article article2 : list) {
					System.out.println("-----------"+article2.getContentType());
				}
				model.addAttribute("picInfo", picInfo);
			}
		});
		
		/**
		 * 显示友情链接
		 */
		t6 = new Thread(new Runnable() {
			public void run() {
				System.out.println("进入链接---------");
				PageInfo<Links> linksInfo = linksService.selects(1, 10);
				//查询最新添加的10条链接
				model.addAttribute("linksInfo", linksInfo);
			}
		});
		
		//封装查询条件
		model.addAttribute("article", article);
		model.addAttribute("categoryId", article.getCategoryId());
		
		//启动线程
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		
		try {
			t1.join();
			t2.join();
			t3.join();
			t4.join();
			t5.join();
			t6.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("首页用时:"+(System.currentTimeMillis()-s1));
		return "index/index";
		
	}
	
	@RequestMapping("article")
	public String article(Model model,@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "3")Integer pageSize,Integer id,HttpServletRequest request) {
		ArticleWithBLOBs article = articleService.selectByPrimaryKey(id);
		PageInfo<Comment> commentInfo = commentService.selects(article.getId(),pageNum,pageSize);
		HttpSession session = request.getSession(false);
		if(null!=session) {
			User user = (User) session.getAttribute("user");
			int result = collectService.selectByText(article.getTitle(),user);
			model.addAttribute("isCollect", result);
		}
		model.addAttribute("article", article);
		model.addAttribute("page", commentInfo);
		model.addAttribute("nums", commentInfo.getNavigatepageNums());
		return "index/article";
		
	}

	/**
	 * 查询具体的图片集
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("articlepic")
	public String articlepic(Model model,Integer id) {
		ArticleWithBLOBs article = articleService.selectByPrimaryKey(id);
		//josn字符串类型,是一个ArticleVO集合
		String content = article.getContent();
		//定义一个ArticleVO类型的集合,有descr属性和图片位置属性
		List<ArticleVO> list = new ArrayList<ArticleVO>();
//		Gson gson = new Gson();
//		JsonArray array = new JsonParser().parse(content).getAsJsonArray();
//		for (JsonElement jsonElement : array) {
//			ArticleVO vo = gson.fromJson(jsonElement, ArticleVO.class);
//			list.add(vo);
//		}
		//将json类型的集合字符串转换为java集合
		list = JSONObject.parseArray(content, ArticleVO.class);
		model.addAttribute("list", list);//图片集包含的图片地址和图片的描述
		model.addAttribute("title", article.getTitle());//图片集的标题
		return "index/articlepic";
	}
	
	@PostMapping("collect")
	@ResponseBody
	public Result<Collect> collect(Collect collect,HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(null==session) {
			return ResultUtil.error(1, "收藏失败,可能登录信息已过期");
		}
		User user = (com.majingji.cms.domain.User) session.getAttribute("user");
		//再进行一次判断
		if(null==user) {
			return	ResultUtil.error(1, "收藏失败,登录可能过期");
		}
		collect.setUser(user);
		collectService.insert(collect);
		return ResultUtil.success();
		
	}
	
	@PostMapping("comment")
	@ResponseBody
	public Result<Comment> comment(Integer articleId,String content,HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(null ==session) {
			return ResultUtil.error(1, "登录可能过期");
		}
		User user = (User) session.getAttribute("user");
		System.out.println(user.getId()+"-----------------------");
		ArticleWithBLOBs article = new ArticleWithBLOBs();
		article.setId(articleId);
		Comment comment = new Comment();
		comment.setUser(user);
		comment.setArticle(article);
		comment.setContent(content);
		comment.setCreated(new Date());
		commentService.insert(comment);
		return ResultUtil.success();
	}
	
}
