package com.catho.jobsearch.resource.domain;

import java.io.Serializable;

import javax.ws.rs.QueryParam;

public class JobFilterParam implements Serializable {

	private static final long serialVersionUID = 1L;

	@QueryParam("q")
	private final String q;
	@QueryParam("qType")
	private final String qType;
	@QueryParam("sortType")
	private final String sortType;
	@QueryParam("orderType")
	private final String orderType;
	@QueryParam("hash")
	private final String hash;
	@QueryParam("size")
	private final String size;

	public JobFilterParam() {
		this.q = null;
		this.qType = null;
		this.sortType = null;
		this.orderType = null;
		this.hash = null;
		this.size = null;
	}

	public JobFilterParam(String q, String qType, String sortType, String orderType, String hash, String size) {
		this.q = q;
		this.qType = qType;
		this.sortType = sortType;
		this.orderType = orderType;
		this.hash = hash;
		this.size = size;
	}

	public String getQ() {
		return q;
	}

	public String getQType() {
		return qType;
	}

	public String getSortType() {
		return sortType;
	}

	public String getOrderType() {
		return orderType;
	}

	public String getHash() {
		return hash;
	}

	public String getSize() {
		return size;
	}

}
