package com.catho.jobsearch.module;

import com.catho.jobsearch.cache.Cache;
import com.catho.jobsearch.cache.CacheInterceptor;
import com.catho.jobsearch.cache.Cached;
import com.catho.jobsearch.cache.impl.JedisCacheImpl;
import com.catho.jobsearch.service.JobService;
import com.catho.jobsearch.service.impl.JobServiceImpl;
import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class ServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Cache.class).to(JedisCacheImpl.class);
		final CacheInterceptor cacheInterceptor = new CacheInterceptor();
		requestInjection(cacheInterceptor);
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(Cached.class), cacheInterceptor);

		bind(JobService.class).to(JobServiceImpl.class);
	}

}
