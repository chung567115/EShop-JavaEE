package com.taotao.portal.controller;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;

/**
 * @author Charle Chung created on 2017年12月19日
 */
@Controller
@RequestMapping(value = "/item")
public class ItemController {
	@Resource
	private ItemService itemService;

	@RequestMapping(value = "/{itemId}")
	public String showItem(@PathVariable Long itemId, Model model) {
		ItemInfo itemInfo = itemService.getItemById(itemId);
		model.addAttribute("item", itemInfo);
		return "item";
	}

	@ResponseBody
	@RequestMapping(value = "/desc/{itemId}", produces = MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
	public String getItemDesc(@PathVariable Long itemId) {
		String result = itemService.getItemDescById(itemId);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/param/{itemId}", produces = MediaType.TEXT_HTML_VALUE + ";charset=UTF-8")
	public String getItemParam(@PathVariable Long itemId) {
		String result = itemService.getItemParamById(itemId);
		return result;
	}

}
