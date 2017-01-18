package com.catho.jobsearch.util;

import java.util.HashMap;
import java.util.Map;

import com.catho.jobsearch.repository.domain.JobFilter;
import com.catho.jobsearch.repository.domain.JobFilterType;
import com.catho.jobsearch.repository.domain.JobSortType;
import com.catho.jobsearch.repository.domain.OrderType;
import com.catho.jobsearch.resource.domain.JobFilterParam;

public class ResourceUtil {

	public static JobFilter jobFilterFrom(JobFilterParam jobFilterParam) {
		final Map<JobFilterType, String> filters = new HashMap<>();
		final JobSortType sortType = jobFilterParam.getSortType() != null
				? JobSortType.valueOf(jobFilterParam.getSortType()) : null;
		final OrderType orderType = jobFilterParam.getOrderType() != null
				? OrderType.valueOf(jobFilterParam.getOrderType()) : null;

		if (jobFilterParam.getQType() != null) {
			filters.put(JobFilterType.valueOf(jobFilterParam.getQType()), jobFilterParam.getQ());
		}

		JobFilter jobFilter = new JobFilter(filters, sortType, orderType, jobFilterParam.getHash(),
				jobFilterParam.getSize());
		return jobFilter;
	}

}
