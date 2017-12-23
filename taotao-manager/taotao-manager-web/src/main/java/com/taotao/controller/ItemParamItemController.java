package com.taotao.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.service.ItemParamItemService;

/**
 * @author Charle Chung created on 2017年12月1日
 */
@Controller
public class ItemParamItemController {

	@Resource
	private ItemParamItemService itemParamItemService;

	@RequestMapping(value = "/showItem/{itemId}")
	public String showItemParam(@PathVariable Long itemId, Model model) {
		String str = itemParamItemService.getItemParamByItemId(itemId);
		model.addAttribute("itemParam", str);
		return "item";
	}
}
