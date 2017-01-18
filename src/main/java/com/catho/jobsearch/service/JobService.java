package com.catho.jobsearch.service;

import com.catho.jobsearch.domain.Job;
import com.catho.jobsearch.domain.JobResult;
import com.catho.jobsearch.repository.domain.JobFilter;

/**
 * Interface that define services methods for Jobs.
 * 
 * @author felipe.serrano
 *
 */
public interface JobService {

	/**
	 * Finds {@link Job} given filters {@link JobFilter}
	 * 
	 * @param filter
	 *            {@link JobFilter} filter used to find jobs.
	 * @return {@link JobResult} with all jobs found and paginated.
	 */
	JobResult find(JobFilter filter);

}
