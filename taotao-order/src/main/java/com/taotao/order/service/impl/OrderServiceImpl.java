package com.taotao.order.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.dao.JedisClient;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

/**
 * @author Charle Chung created on 2017年12月21日
 */
@Service
public class OrderServiceImpl implements OrderService {
	@Resource
	private TbOrderMapper tbOrderMapper;

	@Resource
	private TbOrderItemMapper tbOrderItemMapper;

	@Resource
	private TbOrderShippingMapper tbOrderShippingMapper;

	@Resource
	private JedisClient jedisClient;

	@Value("${ORDER_GEN_KEY}")
	private String ORDER_GEN_KEY;

	@Value("${ORDER_INIT_ID}")
	private String ORDER_INIT_ID;

	@Value("${ORDER_DETAIL_GEN_KEY}")
	private String ORDER_DETAIL_GEN_KEY;

	/**
	 * 创建订单
	 */
	@Override
	public TaotaoResult createOrder(TbOrder tbOrder, List<TbOrderItem> tbOrderItemList,
			TbOrderShipping tbOrderShipping) {
		Date date = new Date();
		// 初始化订单号值
		String str = jedisClient.get(ORDER_GEN_KEY);
		if (StringUtils.isBlank(str)) {
			jedisClient.set(ORDER_GEN_KEY, ORDER_INIT_ID);
		}

		// 用Redis自增长策略获取订单号
		long orderId = jedisClient.incr(ORDER_GEN_KEY);

		// 补全订单
		tbOrder.setOrderId(String.valueOf(orderId));
		// 状态：1未付款 2已付款 3未发货 4已发货 5交易成功 6交易关闭
		tbOrder.setStatus(1);
		tbOrder.setCreateTime(date);
		tbOrder.setUpdateTime(date);
		tbOrder.setBuyerRate(0);// 0未评价 1评价
		// 插入订单
		tbOrderMapper.insert(tbOrder);

		for (TbOrderItem tbOrderItem : tbOrderItemList) {
			// 补全订单明细
			long orderDetailId = jedisClient.incr(ORDER_DETAIL_GEN_KEY);// 可以不用初始化，直接从1开始
			tbOrderItem.setId(String.valueOf(orderDetailId));
			tbOrderItem.setOrderId(String.valueOf(orderId));
			// 插入订单明细
			tbOrderItemMapper.insert(tbOrderItem);
		}

		// 补全订单物流
		tbOrderShipping.setOrderId(String.valueOf(orderId));
		tbOrderShipping.setCreated(date);
		tbOrderShipping.setUpdated(date);
		// 插入订单物流
		tbOrderShippingMapper.insert(tbOrderShipping);

		return TaotaoResult.ok(orderId);
	}

}
