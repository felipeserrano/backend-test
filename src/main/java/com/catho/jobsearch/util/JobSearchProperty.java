package com.catho.jobsearch.util;

import static java.util.Arrays.asList;

import java.util.List;

public enum JobSearchProperty {

	JOBS_FILE("catho.jobs.file", "src/main/resources/vagas.json"), //

	// Cache
	CACHE_ENABLED("catho.cache.enabled", "true"), //
	REDIS_PASSWORD("catho.cache.password", "pass"), //
	REDIS_MASTER_SERVER("catho.cache.master.server", "localhost"), //
	REDIS_MASTER_CONNECTION_POOL_SIZE("catho.cache.master.connection.pool.size", "5"), //
	REDIS_SLAVE_SERVERS("catho.cache.slave.servers", "localhost"), //
	REDIS_SLAVE_CONNECTION_POOL_SIZE("catho.cache.slave.connection.pool.size", "5"), //
	REDIS_CONNECT_TIMEOUT_MS("catho.cache.connect.timeout", "300"), //
	REDIS_DEFAULT_TTL_SECONDS("catho.cache.default.ttl", "300"), //
	REDIS_USE_PASSWORD("catho.cache.use.password", "false"), //
	REDIS_CONNECTION_POOL_MAX_WAIT_MS("catho.cache.connection.pool.max.wait", "100"), //
	REDIS_CONNECTION_POOL_TEST_ON_BORROW("catho.cache.connection.pool.test.on.borrow", "false"), //
	REDIS_PORT("catho.cache.port", "6379");

	private final String key;
	private final String defaultValue;

	private JobSearchProperty(final String key, final String defaultValue) {
		this.key = key;
		this.defaultValue = defaultValue;
	}
	
	public String getKey() {
		return key;
	}

	public String getStringValue() {
		if (System.getenv(key) != null) {
			return System.getenv(key);
		}
		return System.getProperty(key, defaultValue);
	}

	public Integer getIntegerValue() {
		return Integer.valueOf(getStringValue());
	}

	public Long getLongValue() {
		return Long.valueOf(getStringValue());
	}

	public Boolean getBooleanValue() {
		return Boolean.valueOf(getStringValue());
	}

	public List<String> getListValue() {
		return asList(getStringValue().split(","));
	}

}
