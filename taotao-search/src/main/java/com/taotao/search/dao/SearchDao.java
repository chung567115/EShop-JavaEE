package com.taotao.search.dao;
/**
 * @author Charle Chung 
 * created on 2017年12月18日 
 */

import org.apache.solr.client.solrj.SolrQuery;

import com.taotao.search.pojo.SearchResult;

public interface SearchDao {
	SearchResult search(SolrQuery solrQuery) throws Exception;
}
