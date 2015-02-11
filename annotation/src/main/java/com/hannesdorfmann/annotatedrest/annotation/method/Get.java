package com.hannesdorfmann.annotatedrest.annotation.method;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that the annotated method is handling a http GET call
 * @author Hannes Dorfmann
 */

@Target(ElementType.METHOD) @Retention(RetentionPolicy.CLASS) @Documented
public @interface Get {
  /**
   * The path / url. can contain path parameters. Example:
   *<p>
   * <code>
   *   @Get("/car/{id}")
   *   public Car getCar(@Named("id") String id){
   *     return new Car("BMW", "X3");
   *   }
   * </code>
   *</p>
   *
   * @return The path  / url segment
   */
  String value() default "";
}
