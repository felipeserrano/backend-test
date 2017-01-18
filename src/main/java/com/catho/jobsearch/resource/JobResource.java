package com.catho.jobsearch.resource;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.catho.jobsearch.domain.JobResult;
import com.catho.jobsearch.repository.domain.JobFilter;
import com.catho.jobsearch.resource.domain.JobFilterParam;
import com.catho.jobsearch.resource.domain.JobResponse;
import com.catho.jobsearch.service.JobService;
import com.catho.jobsearch.util.ResourceUtil;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Implementation for {@link JobApi}.
 * 
 * @author felipe.serrano
 * @see {@link JobApi}
 */
@Singleton
@Resource
@Path("/v1/job")
@Produces(MediaType.APPLICATION_JSON)
public class JobResource implements JobApi {

	private final JobService jobService;

	@Inject
	public JobResource(JobService jobService) {
		this.jobService = jobService;
	}

	@GET
	@Path("/find")
	@Override
	public JobResponse find(@BeanParam @Valid final JobFilterParam jobFilterParam) {
		final JobFilter filter = createJobFilter(jobFilterParam);
		final JobResult result = jobService.find(filter);
		return createJobResponse(result, filter);
	}

	private JobResponse createJobResponse(JobResult result, JobFilter filter) {
		JobResponse resp = new JobResponse(result.getJobs(), filter.getNavigation(result.getTotal()));
		return resp;
	}

	private JobFilter createJobFilter(JobFilterParam jobFilterParam) {
		return ResourceUtil.jobFilterFrom(jobFilterParam);
	}

}
