package com.taotao.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.JSONUtils;
import com.taotao.service.PictureService;

/**
 * @author Charle Chung created on 2017年11月28日
 */
@Controller
@RequestMapping(value = "/pic")
public class PictureController {
	@Resource
	private PictureService pictureService;

	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value = "/upload")
	public String upload(MultipartFile uploadFile) {
		Map result = pictureService.uploadPicture(uploadFile);
		String json = JSONUtils.objectToJson(result);
		return json;
	}
}
