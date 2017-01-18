package com.catho.jobsearch.domain;

import java.io.Serializable;

public class ErrorResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String errorCode;
	private final String message;

	public ErrorResponse() {
		this.errorCode = null;
		this.message = null;
	}

	public ErrorResponse(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}

}
