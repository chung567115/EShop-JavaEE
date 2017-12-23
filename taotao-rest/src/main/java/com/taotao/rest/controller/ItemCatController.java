package com.taotao.rest.controller;

import javax.annotation.Resource;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;

/**
 * @author Charle Chung created on 2017年12月5日
 */
@Controller
public class ItemCatController {
	@Resource
	private ItemCatService itemCatService;

//	@ResponseBody
//	@RequestMapping(value = "/itemcat/list", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	public String getItemCatList(String callback) {
//		CatResult catResult = itemCatService.getItemCatList();
//		// 生成JSON
//		String json = JSONUtils.objectToJson(catResult);
//
//		// 拼装JSONP返回值
//		String result = callback + "(" + json + ");";
//
//		return result;
//	}

	/******SpringMVC 4.1才有*******/
	@ResponseBody
	@RequestMapping(value = "/itemcat/list")
	public MappingJacksonValue getItemCatList(String callback) {
		CatResult catResult = itemCatService.getItemCatList();
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(catResult);
		mappingJacksonValue.setJsonpFunction(callback);
		return mappingJacksonValue;
	}

}
