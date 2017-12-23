package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;

/**
 * @author Charle Chung created on 2017年12月19日
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;

	@Override
	public SearchResult search(String queryStr, Integer page) {
		// 查询参数
		Map<String, String> param = new HashMap<>();
		param.put("q", queryStr);
		param.put("page", String.valueOf(page));
		try {
			// 调用搜索服务
			String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
			TaotaoResult result = TaotaoResult.formatToPojo(json, SearchResult.class);
			if (result.getStatus() == 200) {
				SearchResult searchResult = (SearchResult) result.getData();
				return searchResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
