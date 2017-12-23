package com.taotao.service;
/**
 * @author Charle Chung 
 * created on 2017年11月22日 
 */

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {

	TbItem getItemById(long itemId);

	EUDataGridResult getItemList(int page, int rows);

	TaotaoResult createItem(TbItem tbItem, String desc, String itemParams) throws Exception;
}
