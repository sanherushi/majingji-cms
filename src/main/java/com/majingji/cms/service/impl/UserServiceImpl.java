package com.majingji.cms.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.majingji.cms.domain.User;
import com.majingji.cms.mapper.UserMapper;
import com.majingji.cms.service.UserService;
import com.majingji.cms.utils.CMSAjaxException;
import com.majingji.cms.utils.CMSException;
import com.majingji.cms.utils.Md5Util;
import com.majingji.cms.vo.UserVO;
import com.majingji.utils.StringUtil;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月13日 下午5:57:15 
* 类功能说明 
*/
@Service
public class UserServiceImpl implements UserService {
	@Resource
	private UserMapper userMapper;
	@Override
	public PageInfo<User> selects(User user,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<User> list = userMapper.selects(user);
		PageInfo<User> page = new PageInfo<User>(list);
		return page;
	}
	@Override
	public boolean update(User user) {
		try {
			userMapper.updateByPrimaryKeySelective(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException("操作失败");
			throw new CMSAjaxException("操作失败", 1);
		}
	}
	@Override
	public boolean insertSelective(UserVO userVO) {
			if(!StringUtil.hasText(userVO.getUsername())) {
				throw new CMSException("用户名不得为空");
			}
			if(!StringUtil.hasText(userVO.getPassword())) {
				throw new CMSException("密码不得为空");
			}
			if(!StringUtil.hasText(userVO.getRepassword())) {
				throw new CMSException("确认密码不得为空");
			}
			if(!userVO.getRepassword().equals(userVO.getPassword())) {
				throw new CMSException("两次密码必须一致");
			}
			User user = userMapper.selectByName(userVO.getUsername());
			if(user!=null) {
				throw new CMSException("此用户名已存在");
			}
			//对传入的密码进行md5加密
			userVO.setPassword(Md5Util.md5Encoding(userVO.getPassword()));
			//对姓注册的用户进行默认值设置
			userVO.setCreated(new Date());
			//昵称默认与用户名相同
			userVO.setNickname(userVO.getUsername());
			return userMapper.insertSelective(userVO)>0;
			
		
	}
	@Override
	public User login(User user) {
		if(!StringUtil.hasText(user.getUsername())) {
			throw new CMSException("用户名不得为空");
		}
		if(!StringUtil.hasText(user.getPassword())) {
			throw new CMSException("密码不得为空");
		}
		User u = userMapper.selectByName(user.getUsername());
		if(null==u) {
			throw new CMSException("此用户名不存在");
		}else if(u.getLocked()==1) {
			throw new CMSException("此用户已被禁用");
		}else if(!Md5Util.md5Encoding(user.getPassword()).equals(Md5Util.md5Encoding(user.getPassword()))) {
			throw new CMSException("输入密码错误");
		}
		return u;
	}
	
}
