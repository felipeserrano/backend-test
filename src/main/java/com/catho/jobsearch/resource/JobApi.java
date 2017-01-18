package com.catho.jobsearch.resource;

import javax.validation.Valid;
import javax.ws.rs.BeanParam;

import com.catho.jobsearch.domain.Job;
import com.catho.jobsearch.repository.domain.JobFilterType;
import com.catho.jobsearch.repository.domain.JobSortType;
import com.catho.jobsearch.repository.domain.OrderType;
import com.catho.jobsearch.resource.domain.JobFilterParam;
import com.catho.jobsearch.resource.domain.JobResponse;

/**
 * Interface which defines job operations.
 * 
 * @author felipe.serrano
 *
 */
public interface JobApi {

	/**
	 * Request a list of {@link Job} given {@link JobFilterParam} parameters. 
	 * QueryString parameters: 
	 * q = the value used to search. 
	 * qType = field used to search a job. available options:
	 * {@link JobFilterType} DESCRIPTION, CITY. 
	 * sortType = field used to sort the result. available options: {@link JobSortType} TITLE, SALARY. 
	 * orderType = field used to define the kind sort.
	 * available options: {@link OrderType} DESC, ASC. 
	 * hash = used to get paginated result. 
	 * size = number of registers returned.
	 * 
	 * @param jobFilterParam
	 *            {@link JobFilterParam} with parameters used to find jobs.
	 * @return {@link JsonResponse} with {@link Job} founded.
	 */
	JobResponse find(@BeanParam @Valid final JobFilterParam jobFilterParam);

}
