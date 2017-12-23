package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;

/**
 * @author Charle Chung created on 2017年12月5日
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Resource
	private TbContentCategoryMapper tbContentCategoryMapper;

	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		// 查询数据库
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);

		// 封装树形节点数据
		List<EUTreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EUTreeNode euTreeNode = new EUTreeNode();
			euTreeNode.setId(tbContentCategory.getId());
			euTreeNode.setParentId(tbContentCategory.getParentId());
			euTreeNode.setText(tbContentCategory.getName());
			euTreeNode.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			resultList.add(euTreeNode);
		}
		return resultList;
	}

	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setIsParent(false);
		tbContentCategory.setName(name);
		tbContentCategory.setStatus(1);
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());

		// 添加记录
		tbContentCategoryMapper.insert(tbContentCategory);

		// 父节点isParent置true
		TbContentCategory parent = tbContentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parent.getIsParent()) {
			parent.setIsParent(true);
			tbContentCategoryMapper.updateByPrimaryKey(parent);
		}

		return TaotaoResult.ok(tbContentCategory);
	}

	@Override
	public TaotaoResult deleteContentCategory(long parentId, long id) {
		// 判断有无兄弟节点
		TbContentCategoryExample brotherExample = new TbContentCategoryExample();
		Criteria brotherCriteria = brotherExample.createCriteria();
		brotherCriteria.andParentIdEqualTo(parentId);
		brotherCriteria.andIdNotEqualTo(id);
		List<TbContentCategory> brotherList = tbContentCategoryMapper.selectByExample(brotherExample);
		// 如无其他兄弟节点，将父节点置为叶子结点
		if (brotherList.isEmpty()) {
			TbContentCategory parent = new TbContentCategory();
			parent.setId(parentId);
			parent.setIsParent(false);
			tbContentCategoryMapper.updateByPrimaryKeySelective(parent);
		}

		// 删除当前节点及其所有子节点
		deleteNode(id);

		return TaotaoResult.ok();
	}

	/**** 递归方法 ****/
	private void deleteNode(Long id) {
		// 取所有子节点
		TbContentCategoryExample sonExample = new TbContentCategoryExample();
		Criteria sonCriteria = sonExample.createCriteria();
		sonCriteria.andParentIdEqualTo(id);
		List<TbContentCategory> sonList = tbContentCategoryMapper.selectByExample(sonExample);

		// 递归删除所有子节点
		for (TbContentCategory tbContentCategory : sonList) {
			deleteNode(tbContentCategory.getId());
		}

		// 删除当前节点
		tbContentCategoryMapper.deleteByPrimaryKey(id);
	}

	@Override
	public TaotaoResult updateContentCategory(long id, String name) {
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setId(id);
		tbContentCategory.setName(name);

		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);

		tbContentCategoryMapper.updateByExampleSelective(tbContentCategory, example);

		return TaotaoResult.ok();
	}

}
