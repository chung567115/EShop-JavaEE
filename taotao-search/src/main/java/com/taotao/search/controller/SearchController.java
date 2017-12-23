package com.taotao.search.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;

/**
 * @author Charle Chung created on 2017年12月18日
 */
@Controller
public class SearchController {
	@Resource
	private SearchService searchService;

	@ResponseBody
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public TaotaoResult search(@RequestParam(value = "q") String queryStr,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "rows", defaultValue = "60") Integer rows) {

		if (StringUtils.isBlank(queryStr)) {
			return TaotaoResult.build(400, "查询条件不能为空");
		}
		SearchResult searchResult = null;
		try {
			queryStr = new String(queryStr.getBytes("ISO8859-1"), "UTF-8");
			searchResult = searchService.search(queryStr, page, rows);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		return TaotaoResult.ok(searchResult);
	}

}
