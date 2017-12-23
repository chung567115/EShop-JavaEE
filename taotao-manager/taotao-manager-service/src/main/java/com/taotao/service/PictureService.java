package com.taotao.service;
/**
 * @author Charle Chung 
 * created on 2017年11月28日 
 */

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
	@SuppressWarnings("rawtypes")
	Map uploadPicture(MultipartFile uploadFile);
}
