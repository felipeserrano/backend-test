package com.catho.jobsearch.repository.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.catho.jobsearch.domain.Job;
import com.catho.jobsearch.repository.JobRepository;
import com.catho.jobsearch.repository.domain.JobFilter;
import com.catho.jobsearch.repository.domain.JobFilterType;
import com.catho.jobsearch.repository.domain.OrderType;
import com.catho.jobsearch.util.Deserializer;
import com.catho.jobsearch.util.JobSearchProperty;
import com.catho.jobsearch.util.SerializerDeserializerUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

/**
 * File job repository implementation. Load all jobs from a static file.
 * 
 * @author felipe.serrano
 * @see {@link JobRepository}
 */
public class FileJobRepositoryImpl implements JobRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileJobRepositoryImpl.class);

	private final Collection<Job> jobs;

	public FileJobRepositoryImpl() {
		this(JobFileUtil.readJobs());
	}

	public FileJobRepositoryImpl(Collection<Job> jobs) {
		this.jobs = jobs;
	}

	@Override
	public Collection<Job> find(JobFilter filter) {
		return jobs.stream().filter(j -> {
			boolean q = filter.getFilters().isEmpty() ? true : false;
			if (filter.getFilters().containsKey(JobFilterType.DESCRIPTION)) {
				final String desc = filter.getFilters().get(JobFilterType.DESCRIPTION);
				q = StringUtils.containsIgnoreCase(j.getTitle(), desc);
				q = q || StringUtils.containsIgnoreCase(j.getDescription(), desc);
			}
			if (filter.getFilters().containsKey(JobFilterType.CITY)) {
				final String city = filter.getFilters().get(JobFilterType.CITY);
				q = q || j.getCidade().parallelStream().anyMatch(c -> StringUtils.containsIgnoreCase(c, city));
			}
			return q;
		}).sorted((j1, j2) -> {
			switch (filter.getSortType()) {
			case SALARY:
				if (filter.getOrderType() == OrderType.ASC) {
					return j1.getSalario().compareTo(j2.getSalario());
				}
				return j2.getSalario().compareTo(j1.getSalario());
			case TITLE:
			default:
				return j1.getTitle().compareTo(j2.getTitle());
			}

		}).collect(Collectors.toList());
	}

	static class JobFileUtil {

		private static final Deserializer deserializer = SerializerDeserializerUtil.getInstance();;

		private JobFileUtil() {
		}

		public static Collection<Job> readJobs() {
			try {
				return ((Resp) deserializer.fromJson(
						new FileInputStream(JobSearchProperty.JOBS_FILE.getStringValue()),
						Resp.class)).getDocs();
			} catch (JsonIOException e) {
				LOGGER.error(e.getMessage(), e);
			} catch (JsonSyntaxException e) {
				LOGGER.error(e.getMessage(), e);
			} catch (FileNotFoundException e) {
				LOGGER.error(e.getMessage(), e);
			}
			return Collections.<Job> emptyList();
		}
	}

	class Resp implements Serializable {
		private static final long serialVersionUID = 1L;

		private final Collection<Job> docs;

		public Resp() {
			this.docs = null;
		}

		public Resp(@JsonProperty("docs") Collection<Job> docs) {
			this.docs = docs;
		}

		public Collection<Job> getDocs() {
			return docs;
		}

	}

}
