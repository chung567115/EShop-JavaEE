package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;

/**
 * 商品分类管理树形列表
 * 
 * @author Charle Chung created on 2017年11月26日
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Resource
	private TbItemCatMapper tbItemCatMapper;

	@Override
	public List<EUTreeNode> getCatList(long parentId) {
		// 创建查询条件
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);

		// 根据条件查询
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList<>();
		// 转换成treeNodeList
		for (TbItemCat tbItemCat : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(tbItemCat.getId());
			node.setParentId(tbItemCat.getParentId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent() ? "closed" : "open");
			resultList.add(node);
		}

		return resultList;
	}

}
