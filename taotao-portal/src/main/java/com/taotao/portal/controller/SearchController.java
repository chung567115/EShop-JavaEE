package com.taotao.portal.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;

/**
 * @author Charle Chung created on 2017年12月19日
 */
@Controller
public class SearchController {
	@Resource
	private SearchService searchService;

	@RequestMapping(value = "/search")
	public String search(@RequestParam(value = "q") String queryStr,
			@RequestParam(value = "page", defaultValue = "1") Integer page, Model model) {

		if (!StringUtils.isBlank(queryStr)) {
			try {
				queryStr = new String(queryStr.getBytes("ISO8859-1"), "UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		SearchResult searchResult = searchService.search(queryStr, page);

		// 向页面传递参数
		model.addAttribute("query", queryStr);
		model.addAttribute("totalPages", searchResult.getPageCount());
		model.addAttribute("itemList", searchResult.getItemList());
		model.addAttribute("page", page);

		return "search";
	}

}
