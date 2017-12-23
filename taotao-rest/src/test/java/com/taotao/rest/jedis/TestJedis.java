package com.taotao.rest.jedis;

import java.util.HashSet;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * @author Charle Chung created on 2017年12月13日
 */
public class TestJedis {

	@Test
	public void testJedisSingle() {
		// 创建Jedis对象
		Jedis jedis = new Jedis("172.17.0.231", 6379);

		// 调用Jedis对象方法（方法名和Redis命令一致）
		jedis.set("key1", "value1");
		String str = jedis.get("key1");
		System.out.println(str);

		// 关闭Jedis
		jedis.close();
	}

	// 连接池
	@Test
	public void testJedisPool() {
		// 创建JedisPool
		JedisPool jedisPool = new JedisPool("172.17.0.231", 6379);

		// 从连接池获得Jedis对象
		Jedis jedis = jedisPool.getResource();

		String str = jedis.get("key1");
		System.out.println("pool:" + str);

		// 关闭
		jedis.close();
		jedisPool.close();
	}

	// 集群
	@Test
	public void testJedisCluster() {
		// 节点
		HashSet<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("172.17.0.231", 7001));
		nodes.add(new HostAndPort("172.17.0.231", 7002));
		nodes.add(new HostAndPort("172.17.0.231", 7003));
		nodes.add(new HostAndPort("172.17.0.231", 7004));
		nodes.add(new HostAndPort("172.17.0.231", 7005));
		nodes.add(new HostAndPort("172.17.0.231", 7006));

		// JedisCluster
		JedisCluster jedisCluster = new JedisCluster(nodes);

		jedisCluster.set("key1", "value2");
		String str = jedisCluster.get("key1");
		System.out.println(str);

		jedisCluster.close();
	}

	// 整合Spring单机版测试
	@Test
	public void testSingleJedisAtSpring() {
		AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		JedisPool jedisPool = (JedisPool) applicationContext.getBean("redisClient");
		Jedis jedis = jedisPool.getResource();
		String str = jedis.get("key1");
		System.out.println(str);

		jedis.close();
		jedisPool.close();
		applicationContext.close();
	}

	// 整合Spring集群版测试
	@Test
	public void testClusterJedisAtSpring() {
		AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		JedisCluster jedisCluster = (JedisCluster) applicationContext.getBean("redisClient");
		String str = jedisCluster.get("key1");
		System.out.println(str);

		jedisCluster.close();
		applicationContext.close();

	}

}
