package com.taotao.order.dao.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.taotao.order.dao.JedisClient;

import redis.clients.jedis.JedisCluster;

/**
 * @author Charle Chung created on 2017年12月13日
 */
@Repository
public class JedisClientCluster implements JedisClient {

	@Resource
	private JedisCluster jedisCluster;

	@Override
	public String get(String key) {
		return jedisCluster.get(key);
	}

	@Override
	public String set(String key, String value) {
		return jedisCluster.set(key, value);
	}

	@Override
	public String hget(String key, String field) {
		return jedisCluster.hget(key, field);
	}

	@Override
	public long hset(String key, String field, String value) {
		return jedisCluster.hset(key, field, value);
	}

	@Override
	public long incr(String key) {
		return jedisCluster.incr(key);
	}

	@Override
	public long expire(String key, int seconds) {
		return jedisCluster.expire(key, seconds);
	}

	@Override
	public long ttl(String key) {
		return jedisCluster.ttl(key);
	}

	@Override
	public long del(String key) {
		return jedisCluster.del(key);
	}

	@Override
	public long hdel(String key, String field) {
		return jedisCluster.hdel(key, field);
	}

}
