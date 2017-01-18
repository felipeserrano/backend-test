package com.catho.jobsearch.repository.domain;

import java.io.Serializable;

public class Navigation implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String prev;
	private final String next;

	public Navigation() {
		this.prev = null;
		this.next = null;
	}

	public Navigation(String prev, String next) {
		this.prev = prev;
		this.next = next;
	}

	public String getPrev() {
		return prev;
	}

	public String getNext() {
		return next;
	}

}