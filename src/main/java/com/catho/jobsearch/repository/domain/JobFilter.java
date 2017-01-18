package com.catho.jobsearch.repository.domain;

import java.util.HashMap;
import java.util.Map;

public class JobFilter extends QueryFilter {

	private static final long serialVersionUID = 1L;

	private final static JobSortType DEFAULT_SORT_TYPE = JobSortType.TITLE;
	private final static OrderType DEFAULT_ORDER_TYPE = OrderType.ASC;

	private final Map<JobFilterType, String> filters;
	private final JobSortType sortType;
	private final OrderType orderType;

	public JobFilter() {
		super();
		this.filters = new HashMap<>();
		this.sortType = DEFAULT_SORT_TYPE;
		this.orderType = DEFAULT_ORDER_TYPE;
	}

	public JobFilter(Map<JobFilterType, String> filters, JobSortType sortType, OrderType orderType, String hash,
			String size) {
		super(hash, size);
		this.filters = filters == null ? new HashMap<>() : filters;
		this.sortType = sortType == null ? DEFAULT_SORT_TYPE : sortType;
		this.orderType = orderType == null ? DEFAULT_ORDER_TYPE : orderType;
	}

	public Map<JobFilterType, String> getFilters() {
		return filters;
	}

	public JobSortType getSortType() {
		return sortType;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	@Override
	public Pagination getPagination() {
		return super.getPagination();
	}

	@Override
	public Navigation getNavigation(int currentResultSize) {
		return new NavigationUtil().build(super.getPagination(), currentResultSize);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((filters == null) ? 0 : filters.hashCode());
		result = prime * result + ((orderType == null) ? 0 : orderType.hashCode());
		result = prime * result + ((sortType == null) ? 0 : sortType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobFilter other = (JobFilter) obj;
		if (filters == null) {
			if (other.filters != null)
				return false;
		} else if (!filters.equals(other.filters))
			return false;
		if (orderType != other.orderType)
			return false;
		if (sortType != other.sortType)
			return false;
		return true;
	}

}
