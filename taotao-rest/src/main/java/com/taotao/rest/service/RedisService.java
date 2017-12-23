package com.taotao.rest.service;
/**
 * @author Charle Chung 
 * created on 2017年12月14日 
 */

import com.taotao.common.pojo.TaotaoResult;

public interface RedisService {
	TaotaoResult syncContent(long contentCid);
}
