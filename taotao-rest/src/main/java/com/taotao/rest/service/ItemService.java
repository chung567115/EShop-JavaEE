package com.taotao.rest.service;
/**
 * @author Charle Chung 
 * created on 2017年12月19日 
 */

import com.taotao.common.pojo.TaotaoResult;

public interface ItemService {
	TaotaoResult getItemBaseInfo(Long itemId);

	TaotaoResult getItemDescInfo(Long itemId);

	TaotaoResult getItemParamInfo(Long itemId);
}
