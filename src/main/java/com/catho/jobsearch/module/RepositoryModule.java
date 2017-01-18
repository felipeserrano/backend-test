package com.catho.jobsearch.module;

import com.catho.jobsearch.repository.JobRepository;
import com.catho.jobsearch.repository.JobRepositoryFactory;
import com.google.inject.AbstractModule;

public class RepositoryModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(JobRepository.class).toInstance(JobRepositoryFactory.create());
	}

}
