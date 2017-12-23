package com.taotao.search.service.impl;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Service;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;

/**
 * @author Charle Chung created on 2017年12月18日
 */
@Service
public class SearchServiceImpl implements SearchService {
	@Resource
	private SearchDao searchDao;

	@Override
	public SearchResult search(String queryStr, int page, int rows) throws Exception {
		SolrQuery solrQuery = new SolrQuery();
		// 查询条件
		solrQuery.setQuery(queryStr);
		// 分页
		solrQuery.setStart((page - 1) * rows);
		solrQuery.setRows(rows);
		// 设置默认查询域
		solrQuery.set("df", "item_keywords");
		// 设置高亮
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");
		solrQuery.setHighlightSimplePre("<em style='color:orange'>");
		solrQuery.setHighlightSimplePost("</em>");
		// 执行查询
		SearchResult result = searchDao.search(solrQuery);

		// 计算结果集元素值
		long recordCount = result.getRecordCount();
		long pageCount = recordCount / rows;
		if (recordCount % rows > 0) {
			pageCount++;
		}
		result.setPageCount(pageCount);
		result.setCurPage(page);

		return result;
	}

}
