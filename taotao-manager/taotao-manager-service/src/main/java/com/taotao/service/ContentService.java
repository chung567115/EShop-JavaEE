package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 * @author Charle Chung created on 2017年12月6日
 */
public interface ContentService {
	EUDataGridResult getContentList(Long categoryId, Integer page, Integer rows);

	TaotaoResult insertContent(TbContent tbContent);
}
