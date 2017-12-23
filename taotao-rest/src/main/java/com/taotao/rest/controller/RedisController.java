package com.taotao.rest.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.RedisService;

/**
 * @author Charle Chung created on 2017年12月14日
 */
@Controller
@RequestMapping(value = "/cache/sync")
public class RedisController {
	@Resource
	private RedisService redisService;

	@ResponseBody
	@RequestMapping(value = "/content/{contentCid}")
	public TaotaoResult contentCacheSync(@PathVariable long contentCid) {
		TaotaoResult result = redisService.syncContent(contentCid);
		return result;
	}

}
