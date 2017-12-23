package com.taotao.order.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.order.pojo.Order;
import com.taotao.order.service.OrderService;

/**
 * @author Charle Chung created on 2017年12月21日
 */
@Controller
public class OrderController {
	@Resource
	private OrderService orderService;

	@ResponseBody
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public TaotaoResult createOrder(@RequestBody Order order) {// 接收JSON字符串（JAVA对象）
		try {
			TaotaoResult result = orderService.createOrder(order, order.getOrderItems(), order.getOrderShipping());
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

}
