package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.ContentService;

/**
 * @author Charle Chung created on 2017年12月6日
 */
@Service
public class ContentServiceImpl implements ContentService {
	@Resource
	private TbContentMapper tbContentMapper;

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;

	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;

	@Override
	public EUDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
		// 查询条件
		TbContentExample tbContentExample = new TbContentExample();
		Criteria criteria = tbContentExample.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);

		// 分页处理
		PageHelper.startPage(page, rows);

		// 查询
		List<TbContent> list = tbContentMapper.selectByExample(tbContentExample);

		// 获取页面信息
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);

		// 处理结果集
		EUDataGridResult euDataGridResult = new EUDataGridResult();
		euDataGridResult.setTotal(pageInfo.getTotal());
		euDataGridResult.setRows(list);

		return euDataGridResult;
	}

	@Override
	public TaotaoResult insertContent(TbContent tbContent) {
		tbContent.setCreated(new Date());
		tbContent.setUpdated(new Date());
		tbContentMapper.insert(tbContent);

		// 同步Redis
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + tbContent.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TaotaoResult.ok();
	}

}
