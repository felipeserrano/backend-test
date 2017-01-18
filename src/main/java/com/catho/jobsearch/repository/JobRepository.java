package com.catho.jobsearch.repository;

import java.util.Collection;

import com.catho.jobsearch.domain.Job;
import com.catho.jobsearch.repository.domain.JobFilter;

/**
 * Repository for {@link Job}
 * 
 * @author felipe.serrano
 *
 */
public interface JobRepository {

	/**
	 * Find all {@link Job} based on filters passed {@link JobFilter}
	 * 
	 * @param filter
	 *            {@link JobFilter} filter used to find jobs.
	 * @return {@link Collection} of {@link Job}.
	 */
	Collection<Job> find(JobFilter filter);

}
