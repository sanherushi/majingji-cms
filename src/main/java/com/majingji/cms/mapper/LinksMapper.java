package com.majingji.cms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.majingji.cms.domain.Links;

/** 
* @author 作者:majingji
* @version 创建时间：2019年11月23日 下午4:11:18 
* 类功能说明 
*/
public interface LinksMapper {
	List<Links> selects();

	void insert(Links links);
	@Delete("delete from cms_links where id = #{id}")
	void deleteLinksbyId(@Param("id")Integer id);
}
