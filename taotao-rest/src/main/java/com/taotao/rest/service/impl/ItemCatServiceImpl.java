package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JSONUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;

/**
 * @author Charle Chung created on 2017年12月5日
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Resource
	private TbItemCatMapper tbItemCatMapper;
	

	@Resource
	private JedisClient jedisClient;

	@Value("${ITEM_CAT_REDIS_KEY}")
	private String ITEM_CAT_REDIS_KEY;


	@Override
	public CatResult getItemCatList() {
		CatResult catResult = new CatResult();
		
		//从Redis缓存读 
		try {
			String result = jedisClient.hget(ITEM_CAT_REDIS_KEY, "itemCatList");
			if (!StringUtils.isBlank(result)) {
				List<CatNode> redisList = JSONUtils.jsonToList(result, CatNode.class);
				catResult.setData(redisList);
				return catResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		//从数据库缓存读 
		List<?> list = getCatList(0);
		catResult.setData(list);

		
		//向Redis缓存写 
		try {
			String value = JSONUtils.objectToJson(list);
			jedisClient.hset(ITEM_CAT_REDIS_KEY, "itemCatList", value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return catResult;
	}

	// 查询分类列表
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<?> getCatList(long parentId) {
		// 查询数据库
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);

		// 封装结果集
//		int count = 0;
		List resultList = new ArrayList<>();
		for (TbItemCat tbItemCat : list) {
			if (tbItemCat.getIsParent()) {
				CatNode catNode = new CatNode();
				if (parentId == 0) {
					catNode.setName(
							"<a href='/products/" + tbItemCat.getId() + ".html'>" + tbItemCat.getName() + "</a>");
				} else {
					catNode.setName(tbItemCat.getName());
				}
				catNode.setUrl("/products/" + tbItemCat.getId() + ".html");
				catNode.setItem(getCatList(tbItemCat.getId()));
				resultList.add(catNode);
				 
//				if ((parentId == 0) && (++count >= 14)) {
//					break;
//				}
			} else {
				resultList.add("products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
			}
		}
		return resultList;
	}

}
