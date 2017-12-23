package com.taotao.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taotao.common.utils.JSONUtils;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.service.ItemParamItemService;

/**
 * @author Charle Chung created on 2017年12月1日
 */
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {

	@Resource
	private TbItemParamItemMapper tbItemParamItemMapper;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getItemParamByItemId(Long itemId) {
		// 根据商品ID查询规格参数
		TbItemParamItemExample tbItemParamItemExample = new TbItemParamItemExample();
		Criteria criteria = tbItemParamItemExample.createCriteria();
		criteria.andItemIdEqualTo(itemId);

		// 执行查询
		List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(tbItemParamItemExample);
		if (list == null || list.size() == 0) {
			return "";
		}

		// 取规格参数信息
		TbItemParamItem tbItemParamItem = list.get(0);
		String paramData = tbItemParamItem.getParamData();

		// 生成HTML
		List<Map> jsonList = JSONUtils.jsonToList(paramData, Map.class);
		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
		sb.append("    <tbody>\n");
		for (Map m1 : jsonList) {
			sb.append("        <tr>\n");
			sb.append("            <th class=\"tdTitle\" colspan=\"2\">" + m1.get("group") + "</th>\n");
			sb.append("        </tr>\n");
			List<Map> list2 = (List<Map>) m1.get("params");
			for (Map m2 : list2) {
				sb.append("        <tr>\n");
				sb.append("            <td class=\"tdTitle\">" + m2.get("k") + "</td>\n");
				sb.append("            <td>" + m2.get("v") + "</td>\n");
				sb.append("        </tr>\n");
			}
		}
		sb.append("    </tbody>\n");
		sb.append("</table>");
		return sb.toString();
	}

}
