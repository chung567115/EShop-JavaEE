package com.taotao.order.service;

import java.util.List;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

/**
 * @author Charle Chung created on 2017年12月21日
 */
public interface OrderService {
	TaotaoResult createOrder(TbOrder tbOrder, List<TbOrderItem> tbOrderItemList, TbOrderShipping tbOrderShipping);
}
