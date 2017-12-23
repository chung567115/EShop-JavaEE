package com.taotao.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;

/**
 * @author Charle Chung created on 2017年11月29日
 */
@Controller
@RequestMapping(value = "/item/param")
public class ItemParamController {
	@Resource
	private ItemParamService itemParamService;

	@ResponseBody
	@RequestMapping(value = "/list")
	public EUDataGridResult testText(Integer page, Integer rows) {
		EUDataGridResult result = itemParamService.getItemParamList(page, rows);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/query/itemcatid/{itemCatId}")
	public TaotaoResult getItemParamByCid(@PathVariable Long itemCatId) {
		TaotaoResult result = itemParamService.getItemParamByCid(itemCatId);
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/save/{cid}")
	public TaotaoResult insertItemParam(@PathVariable Long cid, String paramData) {
		// 创建POJO对象
		TbItemParam tbItemParam = new TbItemParam();
		tbItemParam.setItemCatId(cid);
		tbItemParam.setParamData(paramData);
		tbItemParam.setCreated(new Date());
		tbItemParam.setUpdated(new Date());
		TaotaoResult result = itemParamService.insertItemParam(tbItemParam);
		return result;
	}

}
