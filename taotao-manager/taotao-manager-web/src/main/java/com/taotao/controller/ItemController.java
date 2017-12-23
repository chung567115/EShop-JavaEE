package com.taotao.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

/**
 * 商品管理Controller
 * 
 * @author Charle Chung created on 2017年11月22日
 */
@Controller
@RequestMapping(value = "/item")
public class ItemController {

	@Resource
	private ItemService itemService;

	@ResponseBody
	@RequestMapping(value = "/{itemId}")
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public EUDataGridResult getItemList(Integer page, Integer rows) {
		EUDataGridResult euDataGridResult = itemService.getItemList(page, rows);
		return euDataGridResult;
	}

	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public TaotaoResult createItem(TbItem tbItem, String desc, String itemParams) throws Exception {
		TaotaoResult taotaoResult = itemService.createItem(tbItem, desc, itemParams);
		return taotaoResult;
	}

}
