package com.taotao.search.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.ItemService;

/**
 * @author Charle Chung created on 2017年12月18日
 */
@Controller
@RequestMapping(value = "/manager")
public class ItemController {
	@Resource
	private ItemService itemService;

	/**
	 * 导入所有商品数据到索引库
	 */
	@ResponseBody
	@RequestMapping(value = "/importall")
	public TaotaoResult importAllItems() {
		TaotaoResult result = itemService.importAllItems();
		return result;
	}

}
