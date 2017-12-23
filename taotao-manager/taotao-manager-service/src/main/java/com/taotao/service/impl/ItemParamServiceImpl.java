package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.ItemParamVO;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;

/**
 * @author Charle Chung created on 2017年11月29日
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {
	@Resource
	private TbItemParamMapper tbItemParamMapper;
	@Resource
	private TbItemCatMapper TbItemCatMapper;

	@Override
	public EUDataGridResult getItemParamList(int page, int rows) {
		// 查询规格列表
		TbItemParamExample example = new TbItemParamExample();
		// 分页
		PageHelper.startPage(page, rows);
		List<TbItemParam> tbItemParamList = tbItemParamMapper.selectByExampleWithBLOBs(example);
		// 创建VO List
		List<ItemParamVO> itemParamVOList = new ArrayList<>();

		for (TbItemParam tbItemParam : tbItemParamList) {
			String itemCatName = TbItemCatMapper.selectByPrimaryKey(tbItemParam.getItemCatId()).getName();
			ItemParamVO itemParamVO = new ItemParamVO();
			itemParamVO.setId(tbItemParam.getId());
			itemParamVO.setItemCatId(tbItemParam.getItemCatId());
			itemParamVO.setItemCatName(itemCatName);
			itemParamVO.setParamData(tbItemParam.getParamData());
			itemParamVO.setCreated(tbItemParam.getCreated());
			itemParamVO.setUpdated(tbItemParam.getUpdated());
			itemParamVOList.add(itemParamVO);
		}

		// 创建返回对象
		EUDataGridResult euDataGridResult = new EUDataGridResult();
		euDataGridResult.setRows(itemParamVOList);
		// 取记录总数
		PageInfo<TbItemParam> pageInfo = new PageInfo<>(tbItemParamList);
		euDataGridResult.setTotal(pageInfo.getTotal());

		return euDataGridResult;
	}

	@Override
	public TaotaoResult getItemParamByCid(Long cid) {
		TbItemParamExample tbItemParamExample = new TbItemParamExample();
		Criteria criteria = tbItemParamExample.createCriteria();
		criteria.andItemCatIdEqualTo(cid);

		List<TbItemParam> result = tbItemParamMapper.selectByExampleWithBLOBs(tbItemParamExample);

		// 判断是否查询到结果
		if (result != null && result.size() > 0) {
			return TaotaoResult.ok(result.get(0));
		}

		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult insertItemParam(TbItemParam tbItemParam) {
		// 插入数据库
		tbItemParamMapper.insert(tbItemParam);
		return TaotaoResult.ok();
	}

}
