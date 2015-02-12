package com.hannesdorfmann.annotatedrest.annotation.method;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates that the annotated method is handling a http DELETE call
 * @author Hannes Dorfmann
 */
@Target(ElementType.METHOD) @Retention(RetentionPolicy.CLASS) @Documented
public @interface Delete {
  /**
   * The path / url. can contain path parameters. Example:
   *<p>
   * <code>
   *   @Delete("/car/{id}")
   *   public void deleteCar(@Named String id){
   *     // Deletes the car
   *   }
   * </code>
   *</p>
   *
   * @return The path  / url segment
   */
  String value() default "";
}
