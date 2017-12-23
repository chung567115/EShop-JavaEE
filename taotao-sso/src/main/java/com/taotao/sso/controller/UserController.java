package com.taotao.sso.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;

/**
 * @author Charle Chung created on 2017年12月20日
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Resource
	private UserService userService;

	@ResponseBody
	@RequestMapping(value = "/check/{param}/{type}")
	public Object checkData(@PathVariable String param, @PathVariable Integer type, String callback) {
		TaotaoResult result = null;
		// 参数校验
		if (StringUtils.isBlank(param)) {
			result = TaotaoResult.build(400, "校验内容不能为空");
		} else if (type == null) {
			result = TaotaoResult.build(400, "校验类型不能为空");
		} else if (type != 1 && type != 2 && type != 3) {
			result = TaotaoResult.build(400, "校验类型错误");
		} else {// 若参数校验通过
			try {
				result = userService.checkData(param, type);
			} catch (Exception e) {
				result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
			}
		}

		if (callback != null) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		} else {
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public TaotaoResult createUser(TbUser tbUser) {
		try {
			TaotaoResult result = userService.createUser(tbUser);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public TaotaoResult userLogin(HttpServletRequest request, HttpServletResponse response, String username,
			String password) {
		try {
			TaotaoResult result = userService.userLogin(request, response, username, password);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@ResponseBody
	@RequestMapping(value = "/token/{token}")
	public Object getUserByToken(@PathVariable String token, String callback) {
		TaotaoResult result = null;
		try {
			result = userService.getUserByToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}

		if (callback != null) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		} else {
			return result;
		}

	}

	@ResponseBody
	@RequestMapping(value = "/logout/{token}")
	public Object delUserByToken(@PathVariable String token, String callback) {
		TaotaoResult result = null;
		try {
			result = userService.delUserByToken(token);
		} catch (Exception e) {
			e.printStackTrace();
			result = TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		if (callback != null) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		} else {
			return result;
		}
	}

}
