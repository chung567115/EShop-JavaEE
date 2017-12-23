package com.taotao.rest.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JSONUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;

/**
 * @author Charle Chung created on 2017年12月6日
 */
@Service
public class ContentServiceImpl implements ContentService {
	@Resource
	private TbContentMapper tbContentMapper;

	@Resource
	private JedisClient jedisClient;

	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;

	@Override
	public List<TbContent> getContentList(Long contentCid) {
		//从Redis缓存读
		try {
			String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, String.valueOf(contentCid));
			if (!StringUtils.isBlank(result)) {
				List<TbContent> redisList = JSONUtils.jsonToList(result, TbContent.class);
				return redisList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		//从数据库缓存读 
		TbContentExample tbContentExample = new TbContentExample();
		Criteria criteria = tbContentExample.createCriteria();
		criteria.andCategoryIdEqualTo(contentCid);
		List<TbContent> list = tbContentMapper.selectByExample(tbContentExample);

		
		//向Redis缓存写
		try {
			String value = JSONUtils.objectToJson(list);
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, String.valueOf(contentCid), value);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
