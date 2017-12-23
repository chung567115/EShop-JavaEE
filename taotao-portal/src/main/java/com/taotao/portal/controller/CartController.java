package com.taotao.portal.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

/**
 * @author Charle Chung created on 2017年12月21日
 */
@Controller
@RequestMapping(value = "/cart")
public class CartController {
	@Resource
	private CartService cartService;

	@RequestMapping(value = "/add/{itemId}")
	public String addCartItem(@PathVariable(value = "itemId") Long itemId,
			@RequestParam(value = "num", defaultValue = "1") Integer buyNum,
			@RequestParam(value = "setNum", defaultValue = "false") boolean isSetDirectly, HttpServletRequest request,
			HttpServletResponse response) {
		cartService.addCartItem(request, response, itemId, buyNum, isSetDirectly);
		return "redirect:/cart/success.html";
	}

	@RequestMapping(value = "/success")
	public String showSuccess() {
		return "cartSuccess";
	}

	@RequestMapping(value = "/cart")
	public String showCart(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<CartItem> cartItemList = cartService.showCartItemList(request, response);
		model.addAttribute("cartList", cartItemList);
		return "cart";
	}

	@RequestMapping(value = "/delete/{itemId}")
	public String delCartItem(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "itemId") Long itemId) {
		cartService.delCartItem(request, response, itemId);
		return "redirect:/cart/cart.html";
	}

}
