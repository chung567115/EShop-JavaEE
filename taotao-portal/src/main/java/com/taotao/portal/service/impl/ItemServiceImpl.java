package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JSONUtils;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;

/**
 * @author Charle Chung created on 2017年12月19日
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;

	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;

	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;

	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;

	// 查询商品信息
	@Override
	public ItemInfo getItemById(Long itemId) {
		try {
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
			if (!StringUtils.isBlank(json)) {
				TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, ItemInfo.class);

				if (taotaoResult.getStatus() == 200) {
					ItemInfo itemInfo = (ItemInfo) taotaoResult.getData();
					return itemInfo;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// 查询商品描述
	@Override
	public String getItemDescById(Long itemId) {
		try {
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_URL + itemId);
			if (!StringUtils.isBlank(json)) {
				TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemDesc.class);

				if (taotaoResult.getStatus() == 200) {
					TbItemDesc tbItemDesc = (TbItemDesc) taotaoResult.getData();
					return tbItemDesc.getItemDesc();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// 查询商品规格参数
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getItemParamById(Long itemId) {
		try {
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAM_URL + itemId);
			if (!StringUtils.isBlank(json)) {
				TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemParamItem.class);

				if (taotaoResult.getStatus() == 200) {
					TbItemParamItem tbItemParamItem = (TbItemParamItem) taotaoResult.getData();

					String paramData = tbItemParamItem.getParamData();
					// 生成html
					List<Map> jsonList = JSONUtils.jsonToList(paramData, Map.class);
					StringBuffer sb = new StringBuffer();
					sb.append(
							"<table cellpadding=\"10\" cellspacing=\"0\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
					sb.append("<tbody>\n");
					for (Map m1 : jsonList) {
						sb.append("<tr>\n");
						sb.append("<th class=\"tdTitle\" colspan=\"4\" bgcolor=\"#CECECE\">" + m1.get("group")
								+ "</th>\n");
						sb.append("</tr>\n");
						List<Map> list2 = (List<Map>) m1.get("params");
						for (Map m2 : list2) {
							sb.append("<tr>\n");
							sb.append("<td align=\"right\" width=\"35%\">" + m2.get("k") + "</td>\n");
							sb.append("<td align=\"center\" width=\"15%\"></td>\n");
							sb.append("<td align=\"center\" width=\"15%\"></td>\n");
							sb.append("<td align=\"left\" width=\"35%\">" + m2.get("v") + "</td>\n");
							sb.append("</tr>\n");
						}
					}
					sb.append("</tbody>\n");
					sb.append("</table>");
					// 返回html片段
					return sb.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
