package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Charle Chung created on 2017年12月20日
 */
@Controller
@RequestMapping(value = "/page")
public class PageController {

	@RequestMapping(value = "/register")
	public String showRegister() {
		return "register";
	}

	@RequestMapping(value = "/login")
	public String showLogin(String redirect, Model model) {
		model.addAttribute("redirect", redirect);
		return "login";
	}

}
