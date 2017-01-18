package com.catho.jobsearch.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify with method is cachable.
 * 
 * @author felipe.serrano
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Cached {

	/**
	 * 
	 * @return setted expiration time
	 */
	int expire() default -1;

}
