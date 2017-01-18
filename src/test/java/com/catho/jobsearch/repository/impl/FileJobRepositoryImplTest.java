package com.catho.jobsearch.repository.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.catho.jobsearch.domain.Job;
import com.catho.jobsearch.repository.domain.JobFilter;
import com.catho.jobsearch.repository.domain.JobFilterType;
import com.catho.jobsearch.repository.domain.JobSortType;
import com.catho.jobsearch.repository.domain.OrderType;

@RunWith(MockitoJUnitRunner.class)
public class FileJobRepositoryImplTest {

	private FileJobRepositoryImpl impl;

	@Before
	public void setup() {
		impl = new FileJobRepositoryImpl(createJobs());
	}

	private Collection<Job> createJobs() {
		final List<Job> jobs = new ArrayList<Job>();

		jobs.add(new Job("b", "bsd", new BigDecimal(1000), Arrays.asList("Qwe"), Arrays.asList("Qwe")));
		jobs.add(new Job("a", "asd", new BigDecimal(2000), Arrays.asList("Asd", "Zxc"), Arrays.asList("Asd")));
		jobs.add(new Job("c", "csd", new BigDecimal(3000), Arrays.asList("Zxc"), Arrays.asList("Zxc")));
		jobs.add(new Job("as", "zsdss", new BigDecimal(1500), Arrays.asList("Zxc"), Arrays.asList("Zxc")));

		return jobs;
	}

	@Test
	public void shouldFindByTitle() {
		final Map<JobFilterType, String> filters = new HashMap<>();
		filters.put(JobFilterType.DESCRIPTION, "a");

		final JobFilter jobFilter = new JobFilter(filters, JobSortType.TITLE, OrderType.ASC, null, null);
		Collection<Job> jobs = impl.find(jobFilter);

		Assert.assertNotNull(jobs);
		Assert.assertFalse(jobs.isEmpty());
	}

	@Test
	public void shouldFindByDescription() {
		final Map<JobFilterType, String> filters = new HashMap<>();
		filters.put(JobFilterType.DESCRIPTION, "csd");

		final JobFilter jobFilter = new JobFilter(filters, JobSortType.TITLE, OrderType.ASC, null, null);
		Collection<Job> jobs = impl.find(jobFilter);

		Assert.assertNotNull(jobs);
		Assert.assertFalse(jobs.isEmpty());
	}

	@Test
	public void shouldFindOrderByTitle() {
		final Map<JobFilterType, String> filters = new HashMap<>();

		final JobFilter jobFilter = new JobFilter(filters, JobSortType.TITLE, OrderType.ASC, null, null);
		List<Job> jobs = impl.find(jobFilter).stream().collect(Collectors.toList());

		Assert.assertNotNull(jobs);
		Assert.assertFalse(jobs.isEmpty());
		Assert.assertEquals("a", jobs.get(0).getTitle());
		Assert.assertEquals("as", jobs.get(1).getTitle());
		Assert.assertEquals("b", jobs.get(2).getTitle());
		Assert.assertEquals("c", jobs.get(3).getTitle());
	}

	@Test
	public void shouldFindOrderBySalaryDesc() {
		final Map<JobFilterType, String> filters = new HashMap<>();

		final JobFilter jobFilter = new JobFilter(filters, JobSortType.SALARY, OrderType.DESC, null, null);
		List<Job> jobs = impl.find(jobFilter).stream().collect(Collectors.toList());

		Assert.assertNotNull(jobs);
		Assert.assertFalse(jobs.isEmpty());
		Assert.assertEquals(new BigDecimal(3000), jobs.get(0).getSalario());
		Assert.assertEquals(new BigDecimal(2000), jobs.get(1).getSalario());
		Assert.assertEquals(new BigDecimal(1500), jobs.get(2).getSalario());
		Assert.assertEquals(new BigDecimal(1000), jobs.get(3).getSalario());
	}

}
