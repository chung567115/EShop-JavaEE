package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;

/**
 * 商品管理Service
 * 
 * @author Charle Chung created on 2017年11月22日
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Resource
	private TbItemMapper tbItemMapper;

	@Resource
	private TbItemDescMapper tbItemDescMapper;

	@Resource
	private TbItemParamItemMapper tbItemParamItemMapper;

	@Override
	public TbItem getItemById(long itemId) {
		TbItemExample tbItemExample = new TbItemExample();
		Criteria criteria = tbItemExample.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = tbItemMapper.selectByExample(tbItemExample);
		if (list != null && list.size() > 0) {
			TbItem tbItem = list.get(0);
			return tbItem;
		}
		return null;
	}

	@Override
	public EUDataGridResult getItemList(int page, int rows) {
		// 查询商品列表
		TbItemExample tbItemExample = new TbItemExample();

		// 分页处理
		PageHelper.startPage(page, rows);
		List<TbItem> list = tbItemMapper.selectByExample(tbItemExample);

		// 创建一个返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);

		// 取记录总数
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());

		return result;
	}

	@Override
	public TaotaoResult createItem(TbItem tbItem, String desc, String itemParams) throws Exception {
		// 生成商品ID
		Long itemId = IDUtils.genItemId();
		tbItem.setId(itemId);
		// 设置status
		tbItem.setStatus((byte) 1);
		// 设置时间
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		// 添加商品
		tbItemMapper.insert(tbItem);
		// 添加商品描述
		TaotaoResult result1 = insertItemDesc(itemId, desc);
		// 添加规格参数
		TaotaoResult result2 = insertItemParamItem(itemId, itemParams);
		if ((result1.getStatus() != 200) || (result2.getStatus() != 200)) {
			throw new Exception();
		}
		return TaotaoResult.ok();
	}

	private TaotaoResult insertItemDesc(Long itemId, String desc) {
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(itemId);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		tbItemDescMapper.insert(tbItemDesc);
		return TaotaoResult.ok();
	}

	private TaotaoResult insertItemParamItem(Long itemId, String itemParams) {
		// 创建一个POJO
		TbItemParamItem tbItemParamItem = new TbItemParamItem();
		tbItemParamItem.setItemId(itemId);
		tbItemParamItem.setParamData(itemParams);
		tbItemParamItem.setCreated(new Date());
		tbItemParamItem.setUpdated(new Date());
		// 插入数据
		tbItemParamItemMapper.insert(tbItemParamItem);
		return TaotaoResult.ok();
	}

}
