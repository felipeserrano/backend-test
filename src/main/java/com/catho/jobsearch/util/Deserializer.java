package com.catho.jobsearch.util;

import java.io.InputStream;

public interface Deserializer {

	<T> T fromJson(String s, Class<T> clazz);

	<T> T fromJson(InputStream is, Class<T> clazz);
	
	<T> T deserialize(byte[] in);

}
