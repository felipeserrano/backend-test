package com.catho.jobsearch.repository.domain;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public abstract class QueryFilter implements Serializable, Paginable {

	private static final long serialVersionUID = 1L;

	private final Pagination pagination;

	public QueryFilter() {
		this.pagination = new Pagination();
	}

	public QueryFilter(String hash, String size) {
		this.pagination = new Pagination(tryHash(hash), trySize(size));
	}

	public Pagination getPagination() {
		return pagination;
	}

	public int tryHash(String hash) {
		try {
			if (StringUtils.isNotBlank(hash)) {
				return Integer.parseInt(hash.substring(0, hash.length() - 1));
			}
			return 0;
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	private int trySize(String size) {
		try {
			return Integer.parseInt(size);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	class NavigationUtil {

		public NavigationUtil() {

		}

		public Navigation build(Pagination pagination, int currentResultSize) {
			return new Navigation(prev(pagination), next(pagination, currentResultSize));
		}

		private String next(Pagination current, int currentResultSize) {
			if (currentResultSize >= pagination.getSize()) {
				return new StringBuilder().append(current.getEnd()).append("N").toString();
			}
			return null;
		}

		private String prev(Pagination current) {
			if (current.getBegin() - current.getSize() > 0) {
				return new StringBuilder().append(current.getBegin() - current.getSize()).append("P").toString();
			}
			return null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pagination == null) ? 0 : pagination.hashCode());
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
		QueryFilter other = (QueryFilter) obj;
		if (pagination == null) {
			if (other.pagination != null)
				return false;
		} else if (!pagination.equals(other.pagination))
			return false;
		return true;
	}

}
