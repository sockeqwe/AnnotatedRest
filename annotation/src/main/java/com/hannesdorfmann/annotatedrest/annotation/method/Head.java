package com.hannesdorfmann.annotatedrest.annotation.method;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that the annotated method is handling a http HEAD call
 * @author Hannes Dorfmann
 */
@Target(ElementType.METHOD) @Retention(RetentionPolicy.CLASS) @Documented
public @interface Head {
  /**
   * The path / url. can contain path parameters. Example:
   *<p>
   * <code>
   *   @Head("/car/{id}")
   *   public void existsCar(@Named String id){
   *     // check for car id
   *   }
   * </code>
   *</p>
   *
   * @return The path  / url segment
   */
  String value() default "";
}
