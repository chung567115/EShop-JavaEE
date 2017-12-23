package com.taotao.rest.jedis;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * @author Charle Chung created on 2017年12月15日
 */
public class TestSolrJ {

	@Test
	public void addDocument() throws Exception {
		// 创建连接
		SolrServer solrServer = new HttpSolrServer("http://172.17.0.231:8080/solr");
		// 创建文档
		SolrInputDocument solrInputDocument = new SolrInputDocument();
		solrInputDocument.addField("id", "test001");
		solrInputDocument.addField("item_title", "测试1");
		solrInputDocument.addField("item_price", 54321);
		// 文档写入索引库
		solrServer.add(solrInputDocument);
		// 提交
		solrServer.commit();
	}

	@Test
	public void deleteDocument() throws Exception {
		// 创建连接
		SolrServer solrServer = new HttpSolrServer("http://172.17.0.231:8080/solr");
		// solrServer.deleteById("test001");
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}

	@Test
	public void queryDocument() throws Exception {
		SolrServer solrServer = new HttpSolrServer("http://172.17.0.231:8080/solr");
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("*:*");
		solrQuery.setStart(20);
		solrQuery.setRows(50);
		QueryResponse result = solrServer.query(solrQuery);
		SolrDocumentList solrDocumentList = result.getResults();
		System.out.println("共查到数据" + solrDocumentList.getNumFound() + "条\n");
		for (SolrDocument solrDocument : solrDocumentList) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_image"));
		}
	}
}
