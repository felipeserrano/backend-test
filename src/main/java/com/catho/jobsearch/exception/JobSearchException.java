package com.catho.jobsearch.exception;

/**
 * Base class to define app exceptions.
 * 
 * @author felipe.serrano
 *
 */
public class JobSearchException extends RuntimeException {

	private static final String DEFAULT_ERROR_CODE = "0";

	private static final long serialVersionUID = -221569523858572417L;

	private final int statusCode;
	private final String code;
	private final String message;

	public JobSearchException(String message) {
		this(null, 0, DEFAULT_ERROR_CODE, message);
	}

	public JobSearchException(int statusCode, String message) {
		this(null, statusCode, DEFAULT_ERROR_CODE, message);
	}

	public JobSearchException(int statusCode, String code, String message) {
		this(null, statusCode, code, message);
	}

	public JobSearchException(Throwable e) {
		this(e, 0);
	}

	public JobSearchException(Throwable e, int statusCode) {
		this(e, statusCode, DEFAULT_ERROR_CODE, "generic error");
	}

	public JobSearchException(Throwable e, int statusCode, String code, String message) {
		super(message, e);
		this.statusCode = statusCode;
		this.code = code;
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
