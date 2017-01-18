package com.catho.jobsearch.service.impl;

import java.util.Collection;
import java.util.stream.Collectors;

import com.catho.jobsearch.cache.Cached;
import com.catho.jobsearch.domain.Job;
import com.catho.jobsearch.domain.JobResult;
import com.catho.jobsearch.exception.JobSearchNotFoundException;
import com.catho.jobsearch.repository.JobRepository;
import com.catho.jobsearch.repository.domain.JobFilter;
import com.catho.jobsearch.service.JobService;
import com.google.inject.Inject;

public class JobServiceImpl implements JobService {

	private final JobRepository jobRepository;

	@Inject
	public JobServiceImpl(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	@Override
	@Cached(expire = 30)
	public JobResult find(JobFilter filter) {
		final Collection<Job> jobs = jobRepository.find(filter);
		if (jobs == null || jobs.isEmpty()) {
			throw new JobSearchNotFoundException("no jobs found");
		}
		final int begin = filter.getPagination().getBegin();
		final int end = defineEnd(filter, jobs);

		return createResult(jobs, begin, end);
	}

	private JobResult createResult(final Collection<Job> jobs, final int begin, final int end) {
		final Collection<Job> pagedJobs = jobs.stream().collect(Collectors.toList()).subList(begin, end);
		return new JobResult(pagedJobs.toArray(new Job[pagedJobs.size()]), jobs.size());
	}

	private int defineEnd(JobFilter filter, final Collection<Job> jobs) {
		if (filter.getPagination().getEnd() >= jobs.size()) {
			return jobs.size() - 1;
		} else {
			return filter.getPagination().getEnd();
		}
	}
}
