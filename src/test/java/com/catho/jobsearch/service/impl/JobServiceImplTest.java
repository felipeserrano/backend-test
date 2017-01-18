package com.catho.jobsearch.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.catho.jobsearch.domain.Job;
import com.catho.jobsearch.domain.JobResult;
import com.catho.jobsearch.repository.JobRepository;
import com.catho.jobsearch.repository.domain.JobFilter;
import com.catho.jobsearch.repository.domain.JobFilterType;
import com.catho.jobsearch.repository.domain.JobSortType;
import com.catho.jobsearch.repository.domain.OrderType;
import com.catho.jobsearch.service.JobService;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class JobServiceImplTest {

	private JobService impl;

	@Mock
	private JobRepository jobRepository;

	@Before
	public void setup() {
		impl = new JobServiceImpl(jobRepository);
	}

	private Collection<Job> jobs() {
		final List<Job> jobs = new ArrayList<Job>();

		jobs.add(new Job("b", "bsda", new BigDecimal(1000), Arrays.asList("Qwe"), Arrays.asList("Qwe")));
		jobs.add(new Job("a", "asda", new BigDecimal(2000), Arrays.asList("Asd", "Zxc"), Arrays.asList("Asd")));
		jobs.add(new Job("c", "csda", new BigDecimal(3000), Arrays.asList("Zxc"), Arrays.asList("Zxc")));
		jobs.add(new Job("as", "zsdssa", new BigDecimal(1500), Arrays.asList("Zxc"), Arrays.asList("Zxc")));

		return jobs;
	}

	@Test
	public void shouldFindJobs() {
		final Map<JobFilterType, String> filters = new HashMap<>();
		filters.put(JobFilterType.DESCRIPTION, "a");

		final JobFilter jobFilter = new JobFilter(filters, JobSortType.TITLE, OrderType.ASC, null, "3");

		Mockito.when(jobRepository.find(Mockito.any(JobFilter.class))).thenReturn(jobs());

		final JobResult jobs = impl.find(jobFilter);

		Assert.assertNotNull(jobs);
		Assert.assertEquals(3, jobs.getJobs().length);
		Assert.assertEquals(4, jobs.getTotal());
	}

}
