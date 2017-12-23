package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JSONUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

/**
 * @author Charle Chung created on 2017年12月21日
 */
@Service
public class CartServiceImpl implements CartService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;

	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;

	@Override
	public TaotaoResult addCartItem(HttpServletRequest request, HttpServletResponse response, Long itemId,
			Integer buyNum, boolean isSetDirectly) {
		CartItem cartItem = null;
		// 取商品信息
		List<CartItem> itemList = getCartItemList(request);
		// 判断购物车中是否存在此商品
		for (CartItem item : itemList) {
			if (item.getId().equals(itemId)) {// 若存在，增加商品数量
				item.setNum(isSetDirectly ? buyNum : (item.getNum() + buyNum));
				cartItem = item;
				break;
			}
		}
		if (null == cartItem) {// 如果购物车中不存在此商品
			cartItem = new CartItem();
			// 根据商品id查询基本信息
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItem.class);
			if (taotaoResult.getStatus() == 200) {
				TbItem tbItem = (TbItem) taotaoResult.getData();
				cartItem.setId(tbItem.getId());
				cartItem.setImage(tbItem.getImage() == null ? "" : tbItem.getImage().split(",")[0]);
				cartItem.setNum(buyNum);
				cartItem.setPrice(tbItem.getPrice());
				cartItem.setTitle(tbItem.getTitle());
			}
			// 添加到购物车
			itemList.add(cartItem);
		}

		// 购物车列表写入Cookie
		CookieUtils.setCookie(request, response, "TT_CART", JSONUtils.objectToJson(itemList), true);

		return TaotaoResult.ok();
	}

	/**
	 * 从Cookie取商品列表
	 */
	private List<CartItem> getCartItemList(HttpServletRequest request) {
		String cartJosn = CookieUtils.getCookieValue(request, "TT_CART", true);
		if (null == cartJosn) {
			return new ArrayList<>();
		}
		// json转商品列表
		try {
			List<CartItem> list = JSONUtils.jsonToList(cartJosn, CartItem.class);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	// 展示购物车
	@Override
	public List<CartItem> showCartItemList(HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> cartItemList = getCartItemList(request);
		return cartItemList;
	}

	// 删除购物车商品
	@Override
	public TaotaoResult delCartItem(HttpServletRequest request, HttpServletResponse response, Long itemId) {
		// 从Cookie中取商品列表
		List<CartItem> cartItemList = getCartItemList(request);
		for (CartItem cartItem : cartItemList) {
			if (cartItem.getId().equals(itemId)) {
				cartItemList.remove(cartItem);
				break;
			}
		}

		// 重新写入Cookie
		CookieUtils.setCookie(request, response, "TT_CART", JSONUtils.objectToJson(cartItemList), true);

		return TaotaoResult.ok();
	}

}
