package com.taotao.sso.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JSONUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.pojo.TbUserExample.Criteria;
import com.taotao.sso.dao.JedisClient;
import com.taotao.sso.service.UserService;

/**
 * @author Charle Chung created on 2017年12月20日
 */
@Service
public class UserServiceImpl implements UserService {
	@Resource
	private TbUserMapper tbUserMapper;

	@Resource
	private JedisClient jedisClient;

	@Value("${REDIS_USER_SESSION_KEY}")
	private String REDIS_USER_SESSION_KEY;

	@Value("${SSO_SESSION_EXPIRE}")
	private Integer SSO_SESSION_EXPIRE;

	@Override
	public TaotaoResult checkData(String content, Integer type) {
		TbUserExample tbUserExample = new TbUserExample();
		Criteria criteria = tbUserExample.createCriteria();
		switch (type) {
		case 1:
			criteria.andUsernameEqualTo(content);
			break;
		case 2:
			criteria.andPhoneEqualTo(content);
			break;
		case 3:
			criteria.andEmailEqualTo(content);
			break;
		default:
			break;
		}

		List<TbUser> result = tbUserMapper.selectByExample(tbUserExample);

		if (result == null || result.size() == 0) {
			return TaotaoResult.ok(true);
		}

		return TaotaoResult.ok(false);
	}

	@Override
	public TaotaoResult createUser(TbUser tbUser) {
		tbUser.setCreated(new Date());
		tbUser.setUpdated(new Date());
		tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));

		// 插入数据
		tbUserMapper.insert(tbUser);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult userLogin(HttpServletRequest request, HttpServletResponse response, String username,
			String password) {
		TbUserExample tbUserExample = new TbUserExample();
		Criteria criteria = tbUserExample.createCriteria();
		criteria.andUsernameEqualTo(username);
		criteria.andPasswordEqualTo(DigestUtils.md5DigestAsHex(password.getBytes()));

		List<TbUser> list = tbUserMapper.selectByExample(tbUserExample);

		if (list == null || list.size() == 0) {
			return TaotaoResult.build(400, "用户名或密码错误");
		} else {
			// 生成Token
			TbUser tbUser = list.get(0);
			tbUser.setPassword(null);
			String token = UUID.randomUUID().toString();
			jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, JSONUtils.objectToJson(tbUser));
			jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);

			// Cookie
			CookieUtils.setCookie(request, response, "TT_TOKEN", token);

			return TaotaoResult.ok(token);
		}

	}

	@Override
	public TaotaoResult getUserByToken(String token) {
		// 查询Redis缓存
		String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
		if (StringUtils.isBlank(json)) {
			return TaotaoResult.build(400, "session过期，请重新登录");
		} else {
			// 更新过期时间
			jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
			return TaotaoResult.ok(JSONUtils.jsonToPojo(json, TbUser.class));
		}
	}

	@Override
	public TaotaoResult delUserByToken(String token) {
		// 查询Jedis
		String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
		if (!StringUtils.isBlank(json)) {
			// 删除Redis
			jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, 0);
			jedisClient.del(REDIS_USER_SESSION_KEY + ":" + token);
			return TaotaoResult.ok();
		} else {
			return TaotaoResult.build(500, "Redis未删除");
		}
	}

}
