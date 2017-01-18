package com.catho.jobsearch.repository.domain;

public interface Paginable {
	Pagination getPagination();

	Navigation getNavigation(int currentResultSize);
}
