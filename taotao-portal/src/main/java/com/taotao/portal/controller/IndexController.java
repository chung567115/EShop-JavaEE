package com.taotao.portal.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.portal.service.ContentService;

/**
 * @author Charle Chung created on 2017年12月3日
 */
@Controller
public class IndexController {
	@Resource
	private ContentService contentService;

	@RequestMapping(value = "/index")
	public String showIndex(Model model) {
		String ADJson = contentService.getContentList();
		model.addAttribute("ad1", ADJson);
		return "index";
	}

	@ResponseBody
	@RequestMapping(value = "/httpclient/post", method = RequestMethod.POST)
	public String testPost(String username, String password) {
		return "username:" + username + "	password:" + password;
	}
}
