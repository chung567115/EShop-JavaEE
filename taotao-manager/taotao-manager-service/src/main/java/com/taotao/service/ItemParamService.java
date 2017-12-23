package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

/**
 * @author Charle Chung created on 2017年11月29日
 */
public interface ItemParamService {
	EUDataGridResult getItemParamList(int page, int rows);

	TaotaoResult getItemParamByCid(Long cid);

	TaotaoResult insertItemParam(TbItemParam tbItemParam);
}
