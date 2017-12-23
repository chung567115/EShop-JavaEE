package com.taotao.portal.service;
/**
 * @author Charle Chung 
 * created on 2017年12月19日 
 */

import com.taotao.portal.pojo.SearchResult;

public interface SearchService {
	SearchResult search(String queryStr, Integer page);
}
