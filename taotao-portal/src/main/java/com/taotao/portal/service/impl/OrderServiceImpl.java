package com.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JSONUtils;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.OrderService;

/**
 * @author Charle Chung created on 2017年12月21日
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;

	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;

	// 提交订单
	@Override
	public String createOrder(Order order) {
		String result = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, JSONUtils.objectToJson(order));
		TaotaoResult taotaoResult = TaotaoResult.format(result);
		if (taotaoResult.getStatus() == 200) {
			Object orderId = taotaoResult.getData();
			return orderId.toString();
		} else {
			return "";
		}
	}

}
