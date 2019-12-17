package com.majingji.cms.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author 作者:majingji
 * @version 创建时间：2019年11月18日 下午7:27:06 类功能说明
 */
public class Md5Util {
	// 加盐值 :
	private static String salt = "1a2b3c4d";
	public static String md5Encoding(String password) {
		return DigestUtils.md5Hex(password+salt);//将输入的密码进行加密
		
	}
}
