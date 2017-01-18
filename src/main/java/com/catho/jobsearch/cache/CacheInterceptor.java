package com.catho.jobsearch.cache;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.catho.jobsearch.util.JobSearchProperty;
import com.google.inject.Inject;

/**
 * Interceptor which check if method is cacheable ({@link Cached}) and try to get object from cache or set object on cache.
 * 
 * @author felipe.serrano
 *
 */
public class CacheInterceptor implements MethodInterceptor {

	@Inject
	private Cache cache;

	public CacheInterceptor() {

	}

	@Inject
	public CacheInterceptor(Cache cache) {
		this.cache = cache;
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object object = null;
		if (JobSearchProperty.CACHE_ENABLED.getBooleanValue()) {
			try {
				Method method = invocation.getMethod();
				final String methodName = method.toString();
				final String arguments = arguments(invocation.getArguments());
				final String key = String.format("%s-%s", methodName, arguments);
				final Serializable cached = cache.get(key);
				if (cached != null) {
					return cached;
				}
				object = invocation.proceed();
				final Serializable serializable = (Serializable) object;
				if (serializable != null) {
					cache.put(key, serializable, method.getAnnotation(Cached.class).expire());
				}
			} catch (Exception e) {
				// do nothing, return the object or invoke the method
			}
		}
		return object != null ? object : invocation.proceed();
	}

	private String arguments(Object[] objects) {
		final StringBuilder sb = new StringBuilder();
		for (Object obj : objects) {
			sb.append(obj.toString());
		}
		return sb.toString();
	}

}
