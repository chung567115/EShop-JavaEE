package com.taotao.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

/**
 * @author Charle Chung created on 2017年12月6日
 */
@Controller
@RequestMapping(value = "/content")
public class ContentController {
	@Resource
	private ContentService contentService;

	@ResponseBody
	@RequestMapping(value = "/query/list")
	public EUDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
		EUDataGridResult result = contentService.getContentList(categoryId, page, rows);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/save")
	public TaotaoResult insertContent(TbContent tbContent) {
		TaotaoResult result = contentService.insertContent(tbContent);
		return result;
	}

}
