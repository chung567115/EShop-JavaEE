package com.taotao.rest.service;
/**
 * @author Charle Chung 
 * created on 2017年12月6日 
 */

import java.util.List;

import com.taotao.pojo.TbContent;

public interface ContentService {
	List<TbContent> getContentList(Long contentCid);
}
