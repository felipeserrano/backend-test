package com.catho.jobsearch.util;

import java.io.Serializable;

public interface Serializer {

	String toJson(Object o);
	
	byte[] serialize(Serializable o);

}
