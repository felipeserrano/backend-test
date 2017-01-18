package com.catho.jobsearch.cache.impl;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catho.jobsearch.cache.Cache;
import com.catho.jobsearch.util.Deserializer;
import com.catho.jobsearch.util.RedisUtil;
import com.catho.jobsearch.util.Serializer;
import com.catho.jobsearch.util.SerializerDeserializerUtil;
import com.google.inject.Inject;

import redis.clients.jedis.Jedis;

/**
 * Redis cache implementation of {@link Cache} with provides redis operations.
 * 
 * @author felipe.serrano
 * @see {@link Cache}
 */
public class JedisCacheImpl implements Cache {

	private static final Logger LOGGER = LoggerFactory.getLogger(JedisCacheImpl.class);

	private final RedisUtil redisUtil;
	private final Serializer serializer;
	private final Deserializer deserializer;

	public JedisCacheImpl() {
		this(RedisUtil.getInstance(), SerializerDeserializerUtil.getInstance(),
				SerializerDeserializerUtil.getInstance());
	}

	@Inject
	public JedisCacheImpl(RedisUtil redisUtil, Serializer serializer, Deserializer deserializer) {
		this.redisUtil = redisUtil;
		this.serializer = serializer;
		this.deserializer = deserializer;
	}

	@Override
	public <T extends Serializable> T get(String key) {
		final Jedis slave = redisUtil.getJedisRead();
		if (slave == null) {
			return null;
		}
		try {
			LOGGER.debug("getting value with key {}", key);
			final byte[] value = slave.get(key.getBytes());
			if (value == null) {
				return null;
			}
			return deserializer.deserialize(value);
		} catch (Exception e) {
			LOGGER.error("error getting value, message {}", e.getMessage());
			return null;
		} finally {
			slave.close();
		}
	}

	@Override
	public void put(String key, Serializable value, int expirationTime) {
		final Jedis jedis = redisUtil.getJedisWrite();
		if (jedis == null) {
			return;
		}
		try {
			LOGGER.debug("putting value {} in key {} expiration {}", value, key, expirationTime);
			jedis.setex(key.getBytes(), expirationTime, serializer.serialize(value));
		} catch (Exception e) {
			LOGGER.error("error putting value, message: {}", e.getMessage());
		} finally {
			jedis.close();
		}
	}

}
