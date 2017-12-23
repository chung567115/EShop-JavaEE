package com.taotao.service;
/**
 * @author Charle Chung 
 * created on 2017年12月5日 
 */

import java.util.List;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;

public interface ContentCategoryService {
	List<EUTreeNode> getCategoryList(long parentId);

	TaotaoResult insertContentCategory(long parentId, String name);

	TaotaoResult deleteContentCategory(long parentId, long id);

	TaotaoResult updateContentCategory(long id, String name);
}
