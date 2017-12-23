package com.taotao.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;

/**
 * @author Charle Chung created on 2017年12月5日
 */
@Controller
@RequestMapping(value = "/content/category")
public class ContentCategoryController {

	@Resource
	private ContentCategoryService contentCategoryService;

	@ResponseBody
	@RequestMapping(value = "/list")
	public List<EUTreeNode> getContentCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		List<EUTreeNode> result = contentCategoryService.getCategoryList(parentId);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/create")
	public TaotaoResult createContentCategory(Long parentId, String name) {
		TaotaoResult result = contentCategoryService.insertContentCategory(parentId, name);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/delete")
	public TaotaoResult deleteContentCategory(Long parentId, Long id) {
		TaotaoResult result = contentCategoryService.deleteContentCategory(parentId, id);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/update")
	public TaotaoResult updateContentCategory(long id, String name) {
		TaotaoResult result = contentCategoryService.updateContentCategory(id, name);
		return result;
	}

}
