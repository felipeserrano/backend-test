package com.catho.jobsearch.cache;

import java.io.Serializable;

/**
 * Interface which defines cache operations methods.
 * 
 * @author felipe.serrano
 *
 */
public interface Cache {

	/**
	 * Get serializable object from cache.
	 * 
	 * @param key
	 *            to search object in cache.
	 * 
	 * @return serializable object.
	 */
	<T extends Serializable> T get(String key);

	/**
	 * Put serializable object in cache.
	 * 
	 * @param key
	 *            to set the object in cache.
	 * @param value
	 *            serializable object
	 * @param expirationTime
	 *            time in secconds to expire.
	 */
	void put(String key, Serializable value, int expirationTime);

}
