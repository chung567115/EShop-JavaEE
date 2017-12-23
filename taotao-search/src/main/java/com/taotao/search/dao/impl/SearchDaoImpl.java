package com.taotao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Repository;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.Item;
import com.taotao.search.pojo.SearchResult;

/**
 * @author Charle Chung created on 2017年12月18日
 */
@Repository
public class SearchDaoImpl implements SearchDao {
	@Resource
	private SolrServer solrServer;

	@Override
	public SearchResult search(SolrQuery solrQuery) throws Exception {
		SearchResult searchResult = new SearchResult();
		// 查询
		QueryResponse queryResponse = solrServer.query(solrQuery);
		// 获取查询结果
		SolrDocumentList solrDocumentList = queryResponse.getResults();

		// 取高亮显示
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();

		// 封装结果集
		List<Item> itemList = new ArrayList<>();
		for (SolrDocument solrDocument : solrDocumentList) {
			Item item = new Item();

			// 设置Title高亮
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if (list != null && list.size() > 0) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}

			item.setId((String) solrDocument.get("id"));
			item.setTitle(title);
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			item.setCategory_name((String) solrDocument.get("item_category_name"));

			itemList.add(item);
		}

		searchResult.setRecordCount(solrDocumentList.getNumFound());
		searchResult.setItemList(itemList);
		return searchResult;
	}

}
