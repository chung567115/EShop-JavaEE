package com.taotao.rest.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JSONUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;

/**
 * @author Charle Chung created on 2017年12月19日
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Resource
	private TbItemMapper tbItemMapper;

	@Resource
	private TbItemDescMapper tbItemDescMapper;

	@Resource
	private TbItemParamItemMapper tbItemParamItemMapper;

	@Resource
	private JedisClient jedisClient;

	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;

	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;

	@Override
	public TaotaoResult getItemBaseInfo(Long itemId) {
		// 读Redis缓存
		try {
			String result = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":base");
			if (!StringUtils.isBlank(result)) {
				TbItem tbItem = JSONUtils.jsonToPojo(result, TbItem.class);
				// 如果正在访问，重新设置过期时间
				jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base", REDIS_ITEM_EXPIRE);
				return TaotaoResult.ok(tbItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 数据库查
		TbItem item = tbItemMapper.selectByPrimaryKey(itemId);

		// 写Redis缓存
		try {
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base", JSONUtils.objectToJson(item));
			// 设置有效期
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// TaotaoResult封装结果集
		return TaotaoResult.ok(item);
	}

	@Override
	public TaotaoResult getItemDescInfo(Long itemId) {
		// 读Redis缓存
		try {
			String result = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
			if (!StringUtils.isBlank(result)) {
				TbItemDesc tbItemDesc = JSONUtils.jsonToPojo(result, TbItemDesc.class);
				// 如果正在访问，重新设置过期时间
				jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc", REDIS_ITEM_EXPIRE);
				return TaotaoResult.ok(tbItemDesc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 数据库查
		TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);

		// 写Redis缓存
		try {
			jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc", JSONUtils.objectToJson(itemDesc));
			// 设置有效期
			jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 返回结果集
		return TaotaoResult.ok(itemDesc);
	}

	@Override
	public TaotaoResult getItemParamInfo(Long itemId) {
		// 读Redis缓存
		try {
			String result = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
			if (!StringUtils.isBlank(result)) {
				TbItemParamItem tbItemParamItem = JSONUtils.jsonToPojo(result, TbItemParamItem.class);
				// 如果正在访问，重新设置过期时间
				jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
				return TaotaoResult.ok(tbItemParamItem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 数据库查
		TbItemParamItemExample tbItemParamItemExample = new TbItemParamItemExample();
		Criteria criteria = tbItemParamItemExample.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(tbItemParamItemExample);

		if (list != null && list.size() > 0) {
			TbItemParamItem itemParamItem = list.get(0);
			// 写Redis缓存
			try {
				jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JSONUtils.objectToJson(itemParamItem));
				// 设置有效期
				jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return TaotaoResult.ok(itemParamItem);
		}

		// 返回结果集
		return TaotaoResult.build(400, "无此商品规格");
	}
}
