package com.catho.jobsearch.resource.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.catho.jobsearch.domain.ErrorResponse;
import com.catho.jobsearch.exception.JobSearchException;
import com.catho.jobsearch.util.Serializer;
import com.catho.jobsearch.util.SerializerDeserializerUtil;

/**
 * Filter responsible for format the response error message for job app exceptions.
 * 
 * @author felipe.serrano
 *
 */
@WebFilter("/*")
public class JobSearchExceptionFilter implements Filter {

	private static final Serializer serializer = SerializerDeserializerUtil.getInstance();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		final HttpServletRequest request = (HttpServletRequest) servletRequest;

		try {
			chain.doFilter(request, response);
		} catch (Throwable e) {
			if (e.getCause() instanceof JobSearchException) {
				JobSearchException ex = (JobSearchException) e.getCause();

				response.setStatus(ex.getStatusCode());
				response.getWriter().write(serializer.toJson(new ErrorResponse(ex.getCode(), ex.getMessage())));
				response.setContentType("application/json");
				return;
			}
			throw e;
		}
	}

	@Override
	public void destroy() {
	}

}
