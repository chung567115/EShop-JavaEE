package com.taotao.sso.dao;

/**
 * @author Charle Chung created on 2017年12月13日
 */
public interface JedisClient {

	String get(String key);

	String set(String key, String value);

	String hget(String key, String field);

	long hset(String key, String field, String value);

	long incr(String key);

	long expire(String key, int seconds);

	long ttl(String key);

	long del(String key);

	long hdel(String key, String field);
}
