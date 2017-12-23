package com.taotao.portal.service;
/**
 * @author Charle Chung 
 * created on 2017年12月20日 
 */

import com.taotao.pojo.TbUser;

public interface UserService {
	TbUser getUserByToken(String token);
}
