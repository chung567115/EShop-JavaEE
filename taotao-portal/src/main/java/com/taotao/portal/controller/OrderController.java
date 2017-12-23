package com.taotao.portal.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.pojo.TbUser;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;

/**
 * @author Charle Chung created on 2017年12月21日
 */
@Controller
@RequestMapping(value = "/order")
public class OrderController {
	@Resource
	private CartService cartService;

	@Resource
	private OrderService orderService;

	@RequestMapping(value = "/order-cart")
	public String showOrderCart(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<CartItem> cartItemList = cartService.showCartItemList(request, response);
		model.addAttribute("cartList", cartItemList);
		return "order-cart";
	}

	// 提交订单
	@RequestMapping(value = "/create")
	public String createOrder(Order order, Model model, HttpServletRequest request) {
		try {
			// 从request中取拦截器保存的用户信息
			TbUser user = (TbUser) request.getAttribute("user");
			// 补全用户信息
			order.setUserId(user.getId());
			order.setBuyerNick(user.getUsername());

			String orderId = orderService.createOrder(order);
			model.addAttribute("orderId", orderId);
			model.addAttribute("payment", order.getPayment());
			model.addAttribute("date", new DateTime().plusDays(3).toString("yy-MM-dd"));
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "创建订单出错，请返回重试！");
			return "error/exception";
		}
	}

}
