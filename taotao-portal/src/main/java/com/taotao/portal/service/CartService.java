package com.taotao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;

/**
 * @author Charle Chung created on 2017年12月21日
 */
public interface CartService {
	TaotaoResult addCartItem(HttpServletRequest request, HttpServletResponse response, Long itemId, Integer buyNum,
			boolean isSetDirectly);

	List<CartItem> showCartItemList(HttpServletRequest request, HttpServletResponse response);

	TaotaoResult delCartItem(HttpServletRequest request, HttpServletResponse response, Long itemId);
}
