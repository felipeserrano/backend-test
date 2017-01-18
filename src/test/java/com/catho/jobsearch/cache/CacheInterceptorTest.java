package com.catho.jobsearch.cache;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.catho.jobsearch.repository.domain.JobFilter;
import com.catho.jobsearch.service.impl.JobServiceImpl;
import com.catho.jobsearch.util.JobSearchProperty;

@RunWith(MockitoJUnitRunner.class)
public class CacheInterceptorTest {

	@Mock
	private Cache cache;

	@Mock
	private MethodInvocation invocation;

	private CacheInterceptor interceptor;

	@Before
	public void setup() {
		interceptor = new CacheInterceptor(cache);
	}

	@Test
	public void shouldNotTryCachingMethodResponse() throws Throwable {
		System.setProperty(JobSearchProperty.CACHE_ENABLED.getKey(), "false");

		interceptor.invoke(invocation);

		Mockito.verify(cache, Mockito.never()).get(Mockito.anyString());
		Mockito.verify(cache, Mockito.never()).put(Mockito.anyString(), Mockito.any(Serializable.class),
				Mockito.anyInt());
		Mockito.verify(invocation, Mockito.times(1)).proceed();
	}

	@Test
	public void shouldReturnCachedObject() throws Throwable {
		System.setProperty(JobSearchProperty.CACHE_ENABLED.getKey(), "true");

		Method method = JobServiceImpl.class.getMethod("find", JobFilter.class);
		Mockito.when(invocation.getMethod()).thenReturn(method);
		Mockito.when(invocation.getArguments()).thenReturn(new Object[] { new JobFilter() });
		Mockito.when(cache.get(Mockito.anyString())).thenReturn("asd");

		interceptor.invoke(invocation);

		Mockito.verify(cache).get(Mockito.anyString());
		Mockito.verify(cache, Mockito.never()).put(Mockito.anyString(), Mockito.any(Serializable.class),
				Mockito.anyInt());
		Mockito.verify(invocation, Mockito.never()).proceed();
	}

	@Test
	public void shouldPutObjetInCache() throws Throwable {
		System.setProperty(JobSearchProperty.CACHE_ENABLED.getKey(), "true");

		Method method = JobServiceImpl.class.getMethod("find", JobFilter.class);
		Mockito.when(invocation.getMethod()).thenReturn(method);
		Mockito.when(invocation.getArguments()).thenReturn(new Object[] { new JobFilter() });
		Mockito.when(cache.get(Mockito.anyString())).thenReturn(null);
		Mockito.when(invocation.proceed()).thenReturn("asd");

		interceptor.invoke(invocation);

		Mockito.verify(cache).get(Mockito.anyString());
		Mockito.verify(cache).put(Mockito.anyString(), Mockito.any(Serializable.class), Mockito.anyInt());
		Mockito.verify(invocation, Mockito.times(1)).proceed();
	}

	@Test
	public void shouldReturnIfCacheGetFail() throws Throwable {
		System.setProperty(JobSearchProperty.CACHE_ENABLED.getKey(), "true");
		final String value = "asd";

		Method method = JobServiceImpl.class.getMethod("find", JobFilter.class);
		Mockito.when(invocation.getMethod()).thenReturn(method);
		Mockito.when(invocation.getArguments()).thenReturn(new Object[] { new JobFilter() });
		Mockito.doThrow(new RuntimeException()).when(cache).get(Mockito.anyString());
		Mockito.when(invocation.proceed()).thenReturn(value);

		Object result = interceptor.invoke(invocation);

		Assert.assertEquals(value, result);
		Mockito.verify(cache).get(Mockito.anyString());
		Mockito.verify(cache, Mockito.never()).put(Mockito.anyString(), Mockito.any(Serializable.class),
				Mockito.any(int.class));
		Mockito.verify(invocation, Mockito.times(1)).proceed();
	}

}
