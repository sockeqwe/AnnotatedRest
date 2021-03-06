package com.hannesdorfmann.annotatedrest.annotation.header;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Set a response header
 *
 * @author Hannes Dorfmann
 */
@Target(ElementType.METHOD) @Retention(RetentionPolicy.CLASS) @Documented
public @interface CacheMaxAge {

  long value();

  TimeUnit unit() default TimeUnit.SECONDS;
}
