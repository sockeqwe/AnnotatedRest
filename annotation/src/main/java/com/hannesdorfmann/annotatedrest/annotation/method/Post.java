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
public @interface Post {
  /**
   * The path / url. Can contain path parameters like @Get Example:
   *<p>
   * <code>
   *   @Post("/car")
   *   public void newCar(Car car){
   *     // Store the car
   *   }
   * </code>
   *</p>
   *
   * @return The path  / url segment
   */
  String value() default "";
}
