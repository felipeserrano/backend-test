package com.catho.jobsearch.exception;

import org.apache.http.HttpStatus;

public class JobSearchNotFoundException extends JobSearchException {

	private static final long serialVersionUID = -2298351840009043657L;

	public JobSearchNotFoundException(Throwable e, String message) {
		super(e, HttpStatus.SC_NOT_FOUND, "0001", message);
	}

	public JobSearchNotFoundException(Throwable e) {
		super(e, HttpStatus.SC_NOT_FOUND, "0001", "not found");
	}

	public JobSearchNotFoundException(String message) {
		super(HttpStatus.SC_NOT_FOUND, "0001", message);
	}

}
