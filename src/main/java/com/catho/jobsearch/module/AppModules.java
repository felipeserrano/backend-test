package com.catho.jobsearch.module;

import com.catho.jobsearch.resource.JobResource;
import com.catho.jobsearch.util.Deserializer;
import com.catho.jobsearch.util.RedisUtil;
import com.catho.jobsearch.util.Serializer;
import com.catho.jobsearch.util.SerializerDeserializerUtil;
import com.google.inject.Binder;
import com.google.inject.Module;

public class AppModules implements Module {

	@Override
	public void configure(Binder binder) {

		// utils
		binder.bind(RedisUtil.class).toInstance(RedisUtil.getInstance());
		binder.bind(Deserializer.class).toInstance(SerializerDeserializerUtil.getInstance());
		binder.bind(Serializer.class).toInstance(SerializerDeserializerUtil.getInstance());

		// general modules
		binder.install(new RepositoryModule());
		binder.install(new ServiceModule());

		// resources
		binder.bind(JobResource.class);
	}

}
