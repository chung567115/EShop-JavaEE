package com.taotao.rest.controller;
/**
 * @author Charle Chung 
 * created on 2017年12月19日 
 */

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.ItemService;

@Controller
@RequestMapping(value = "/item")
public class ItemController {
	@Resource
	private ItemService itemService;

	@ResponseBody
	@RequestMapping(value = "/info/{itemId}")
	public TaotaoResult getItemBaseInfo(@PathVariable Long itemId) {
		// 从数据库查
		TaotaoResult result = itemService.getItemBaseInfo(itemId);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/desc/{itemId}")
	public TaotaoResult getItemDesc(@PathVariable Long itemId) {
		TaotaoResult result = itemService.getItemDescInfo(itemId);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/param/{itemId}")
	public TaotaoResult getParamDesc(@PathVariable Long itemId) {
		TaotaoResult result = itemService.getItemParamInfo(itemId);
		return result;
	}

}
