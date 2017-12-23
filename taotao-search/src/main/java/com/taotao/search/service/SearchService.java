package com.taotao.search.service;
/**
 * @author Charle Chung 
 * created on 2017年12月18日 
 */

import com.taotao.search.pojo.SearchResult;

public interface SearchService {
	SearchResult search(String queryStr, int page, int rows) throws Exception;
}
