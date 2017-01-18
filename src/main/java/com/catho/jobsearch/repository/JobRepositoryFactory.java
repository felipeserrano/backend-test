package com.catho.jobsearch.repository;

import com.catho.jobsearch.repository.impl.FileJobRepositoryImpl;

public class JobRepositoryFactory {

	public static JobRepository create() {
		final JobRepository fileJobRepository = new FileJobRepositoryImpl();

		return fileJobRepository;
	}

}
