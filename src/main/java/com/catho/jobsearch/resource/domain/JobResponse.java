package com.catho.jobsearch.resource.domain;

import java.io.Serializable;

import com.catho.jobsearch.domain.Job;
import com.catho.jobsearch.repository.domain.Navigation;

public class JobResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Job[] jobs;
	private final Navigation navigation;

	public JobResponse() {
		this.jobs = new Job[0];
		this.navigation = new Navigation();
	}

	public JobResponse(Job[] jobs, Navigation navigation) {
		this.jobs = jobs == null ? new Job[0] : jobs;
		this.navigation = navigation;
	}

	public Job[] getJobs() {
		return jobs;
	}

	public Navigation getNavigation() {
		return navigation;
	}

}
