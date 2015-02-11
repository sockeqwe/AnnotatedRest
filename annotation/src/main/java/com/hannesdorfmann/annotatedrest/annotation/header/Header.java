package com.hannesdorfmann.annotatedrest.annotation.header;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Set a response header
 *
 * @author Hannes Dorfmann
 */
@Target(ElementType.METHOD) @Retention(RetentionPolicy.CLASS) @Documented
public @interface Header {

  /**
   * The header field name without ":" (colon is added automatically)
   * @return
   */
  String name();

  /**
   * The value
   * @return
   */
  String value();
}
