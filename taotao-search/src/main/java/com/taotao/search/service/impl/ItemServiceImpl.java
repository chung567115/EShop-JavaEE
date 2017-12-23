package com.taotao.search.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.pojo.Item;
import com.taotao.search.service.ItemService;

/**
 * @author Charle Chung created on 2017年12月18日
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Resource
	private ItemMapper itemMapper;

	@Resource
	private SolrServer solrServer;

	@Override
	public TaotaoResult importAllItems() {
		try {
			List<Item> itemList = itemMapper.getItemList();
			// 将商品信息写入索引库
			SolrInputDocument solrInputDocument = new SolrInputDocument();
			for (Item item : itemList) {
				solrInputDocument.setField("id", item.getId());
				solrInputDocument.setField("item_title", item.getTitle());
				solrInputDocument.setField("item_sell_point", item.getSell_point());
				solrInputDocument.setField("item_price", item.getPrice());
				solrInputDocument.setField("item_image", item.getImage());
				solrInputDocument.setField("item_category_name", item.getCategory_name());
				solrInputDocument.setField("item_desc", item.getItem_desc());

				// 写入索引库
				solrServer.add(solrInputDocument);
			}
			solrServer.commit();

		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok();
	}

}
