package com.catho.jobsearch.domain;

import java.io.Serializable;
import java.util.Arrays;

public class JobResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Job[] jobs;
	private final int total;

	public JobResult(Job[] jobs, int total) {
		this.jobs = jobs;
		this.total = total;
	}

	public Job[] getJobs() {
		return jobs;
	}

	public int getTotal() {
		return total;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(jobs);
		result = prime * result + total;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobResult other = (JobResult) obj;
		if (!Arrays.equals(jobs, other.jobs))
			return false;
		if (total != other.total)
			return false;
		return true;
	}

}
