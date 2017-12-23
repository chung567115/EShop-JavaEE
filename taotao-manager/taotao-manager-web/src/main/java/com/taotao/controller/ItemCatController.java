package com.taotao.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.service.ItemCatService;

/**
 * @author Charle Chung created on 2017年11月26日
 */
@Controller
@RequestMapping(value = "/item/cat")
public class ItemCatController {
	@Resource
	private ItemCatService itemCatService;

	@ResponseBody
	@RequestMapping(value = "/list")
	public List<EUTreeNode> getCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		List<EUTreeNode> catList = itemCatService.getCatList(parentId);
		return catList;
	}

}
