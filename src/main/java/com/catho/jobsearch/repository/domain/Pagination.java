package com.catho.jobsearch.repository.domain;

import java.io.Serializable;

public class Pagination implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_SIZE = 5;

	private final int begin;
	private final int end;
	private final int size;

	public Pagination() {
		this(0, DEFAULT_SIZE);
	}

	public Pagination(int begin, int size) {
		this.begin = begin < 0 ? 0 : begin;
		this.size = size <= 0 ? DEFAULT_SIZE : size;
		this.end = getBegin() + getSize();
	}

	public int getBegin() {
		return begin;
	}

	public int getEnd() {
		return end;
	}

	public int getSize() {
		return size;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + begin;
		result = prime * result + end;
		result = prime * result + size;
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
		Pagination other = (Pagination) obj;
		if (begin != other.begin)
			return false;
		if (end != other.end)
			return false;
		if (size != other.size)
			return false;
		return true;
	}

}