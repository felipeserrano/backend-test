package com.catho.jobsearch.cache.impl;

import org.junit.Assert;
import org.junit.Test;

import com.catho.jobsearch.util.JobSearchProperty;

import redis.clients.jedis.JedisPool;

public class JedisPoolBuilderTest {

	private static final Integer DEFAULT_REDIS_PORT = 9999;
	private final JedisPoolBuilder poolBuilder = new JedisPoolBuilder();

	@Test
	public void shouldCreateJedisPool() {
		JedisPool pool = poolBuilder.withMinIdle(JobSearchProperty.REDIS_MASTER_CONNECTION_POOL_SIZE.getIntegerValue()) //
				.withMaxIdle(JobSearchProperty.REDIS_MASTER_CONNECTION_POOL_SIZE.getIntegerValue()) //
				.withMaxActive(JobSearchProperty.REDIS_MASTER_CONNECTION_POOL_SIZE.getIntegerValue()) //
				.withMaxWait(JobSearchProperty.REDIS_CONNECTION_POOL_MAX_WAIT_MS.getIntegerValue()) //
				.withTestOnBorrow(JobSearchProperty.REDIS_CONNECTION_POOL_TEST_ON_BORROW.getBooleanValue()) //
				.withConnectionTimeoutMs(JobSearchProperty.REDIS_CONNECT_TIMEOUT_MS.getIntegerValue()) //
				.withServer(JobSearchProperty.REDIS_MASTER_SERVER.getStringValue()) //
				.withPort(DEFAULT_REDIS_PORT) //
				.build();
		Assert.assertNotNull(pool);
	}

}
