package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JSONUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;

/**
 * @author Charle Chung created on 2017年12月6日
 */
@Service
public class ContentServiceImpl implements ContentService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;

	@Value("${REST_INDEX_AD_URL}")
	private String REST_INDEX_AD_URL;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String getContentList() {
		// 调用服务层的服务
		String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);

		// 封装结果
		try {
			TaotaoResult taotaoResult = TaotaoResult.formatToList(result, TbContent.class);
			List<TbContent> list = (List<TbContent>) taotaoResult.getData();
			List<Map> resultList = new ArrayList<>();
			// 创建VO
			for (TbContent tbContent : list) {
				Map map = new HashMap<>();
				map.put("src", tbContent.getPic());
				map.put("width", 670);
				map.put("height", 240);
				map.put("srcB", tbContent.getPic2());
				map.put("widthB", 550);
				map.put("height", 240);
				map.put("href", tbContent.getUrl());
				map.put("alt", tbContent.getTitle());
				resultList.add(map);
			}
			return JSONUtils.objectToJson(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
