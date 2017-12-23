package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

/**
 * @author Charle Chung created on 2017年12月19日
 */
public class ItemInfo extends TbItem {
	public String[] getImages() {
		String image = getImage();
		if (image != null) {
			String[] images = image.split(",");
			return images;
		}
		return null;
	}
}
