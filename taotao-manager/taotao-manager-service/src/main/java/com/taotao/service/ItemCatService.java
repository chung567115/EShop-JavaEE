package com.taotao.service;
/**
 * @author Charle Chung 
 * created on 2017年11月26日 
 */

import java.util.List;

import com.taotao.common.pojo.EUTreeNode;

public interface ItemCatService {
	List<EUTreeNode> getCatList(long parentId);
}
