package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转
 * 
 * @author Charle Chung created on 2017年11月22日
 */
@Controller
public class PageController {
	/**
	 * 打开首页
	 */
	@RequestMapping(value = "/")
	public String showIndex() {
		return "index";
	}

	/**
	 * 打开其他页面
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/{page}")
	public String showPage(@PathVariable String page) {
		return page;
	}
}
