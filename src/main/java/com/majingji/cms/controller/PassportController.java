package com.majingji.cms.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.majingji.cms.domain.User;
import com.majingji.cms.service.UserService;
import com.majingji.cms.utils.CMSException;
import com.majingji.cms.vo.UserVO;

/**
 * @author 作者:majingji
 * @version 创建时间：2019年11月18日 下午4:25:42 类功能说明
 */
@RequestMapping("passport")
@Controller
public class PassportController {
	@Resource
	private UserService userService;

	@GetMapping("login")
	public String login() {
		return "passport/login";
	}

	@PostMapping("login")
	public String login(Model model, HttpSession session, User user) {
		// 使用同一异常处理
		User u = userService.login(user);
		if (u.getRole().equals("1")) {
			session.setAttribute("admin", u);// 将管理员对象保存到session中
			return "redirect:/admin/index";
		} else {
			session.setAttribute("user", u);// 将普通用户对象保存到session中
			return "redirect:/my/index";
		}

	}

	@GetMapping("reg")
	public String reg() {
		return "passport/reg";
	}

	@PostMapping("reg")
	public String reg(UserVO userVO, Model model, RedirectAttributes redirectAttributes) {
		userService.insertSelective(userVO);
		redirectAttributes.addFlashAttribute("username", userVO.getUsername());
		redirectAttributes.addFlashAttribute("message", "注册成功");
		return "redirect:/passport/login";// 重定向到login方法
	}

	@RequestMapping("logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		// 清除所有的session
		session.invalidate();
		return "/passport/login";

	}
}
