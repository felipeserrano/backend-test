package com.catho.jobsearch.util;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catho.jobsearch.cache.impl.JedisPoolBuilder;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

	private static final RedisUtil instance = new RedisUtil();

	private static final JedisPool masterJedisPool = createMasterJedisPool();
	private static final JedisPool[] slaveJedisPools = createSlaveJedisPools();

	private final AtomicInteger indexSlave = new AtomicInteger(-1);

	private RedisUtil() {

	}

	public static RedisUtil getInstance() {
		return instance;
	}

	private static JedisPool createMasterJedisPool() {
		return new JedisPoolBuilder() //
				.withMinIdle(JobSearchProperty.REDIS_MASTER_CONNECTION_POOL_SIZE.getIntegerValue()) //
				.withMaxIdle(JobSearchProperty.REDIS_MASTER_CONNECTION_POOL_SIZE.getIntegerValue()) //
				.withMaxActive(JobSearchProperty.REDIS_MASTER_CONNECTION_POOL_SIZE.getIntegerValue()) //
				.withMaxWait(JobSearchProperty.REDIS_CONNECTION_POOL_MAX_WAIT_MS.getIntegerValue()) //
				.withTestOnBorrow(JobSearchProperty.REDIS_CONNECTION_POOL_TEST_ON_BORROW.getBooleanValue()) //
				.withPassword(JobSearchProperty.REDIS_PASSWORD.getStringValue()) //
				.withConnectionTimeoutMs(JobSearchProperty.REDIS_CONNECT_TIMEOUT_MS.getIntegerValue()) //
				.withServer(JobSearchProperty.REDIS_MASTER_SERVER.getStringValue()) //
				.withPort(JobSearchProperty.REDIS_PORT.getIntegerValue()) //
				.build();
	}

	private static JedisPool[] createSlaveJedisPools() {
		final String[] slaveServers = JobSearchProperty.REDIS_SLAVE_SERVERS.getStringValue().split(",");
		final JedisPool[] slavePools = new JedisPool[(slaveServers.length)];
		int i = 0;
		for (String slaveServer : slaveServers) {
			slavePools[i++] = new JedisPoolBuilder() //
					.withMinIdle(JobSearchProperty.REDIS_SLAVE_CONNECTION_POOL_SIZE.getIntegerValue()) //
					.withMaxIdle(JobSearchProperty.REDIS_SLAVE_CONNECTION_POOL_SIZE.getIntegerValue()) //
					.withMaxActive(JobSearchProperty.REDIS_SLAVE_CONNECTION_POOL_SIZE.getIntegerValue()) //
					.withMaxWait(JobSearchProperty.REDIS_CONNECTION_POOL_MAX_WAIT_MS.getIntegerValue()) //
					.withTestOnBorrow(JobSearchProperty.REDIS_CONNECTION_POOL_TEST_ON_BORROW.getBooleanValue()) //
					.withPassword(JobSearchProperty.REDIS_PASSWORD.getStringValue()) //
					.withConnectionTimeoutMs(JobSearchProperty.REDIS_CONNECT_TIMEOUT_MS.getIntegerValue()) //
					.withServer(slaveServer) //
					.withPort(JobSearchProperty.REDIS_PORT.getIntegerValue()) //
					.build();
		}

		return slavePools;
	}

	public Jedis getJedisWrite() {
		try {
			return masterJedisPool.getResource();
		} catch (Exception e) {
			LOGGER.error("error getting redis connection, message: {}", e.getMessage());
			return null;
		}
	}

	public Jedis getJedisRead() {
		try {
			return getSlaveResource();
		} catch (Exception e) {
			LOGGER.error("error getting redis connection, message: {}", e.getMessage());
			return null;
		}
	}

	private Jedis getSlaveResource() {
		try {
			final int i = Math.abs(indexSlave.incrementAndGet() % slaveJedisPools.length);
			return slaveJedisPools[i].getResource();
		} catch (Exception e) {
			LOGGER.error("error getting redis connection from the slave pool, message {}", e.getMessage());
			return null;
		}
	}

}
