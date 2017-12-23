package com.taotao.portal.service;
/**
 * @author Charle Chung 
 * created on 2017年12月19日 
 */

import com.taotao.portal.pojo.ItemInfo;

public interface ItemService {
	ItemInfo getItemById(Long itemId);

	String getItemDescById(Long itemId);

	String getItemParamById(Long itemId);
}
