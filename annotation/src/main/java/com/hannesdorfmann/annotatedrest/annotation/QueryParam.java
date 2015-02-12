package com.hannesdorfmann.annotatedrest.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to identify the query parameters (at the end of the url with "?"
 * as starting sign, concatinated with "&") like this:
 * <p>
 * <code>
 *
 * @author Hannes Dorfmann
 * @Api public class ThingApi {
 * @Get( path = "/thing/{id}}
 * )
 * public Thing getThing(@Named int id, @QueryParam String foo, @QueryParam int limit){
 * ...
 * }
 * }
 * </code>
 * </p>
 *
 * <p>
 * This will map a url like this: <i>/thing/123?foo=abc&limit=5</i>
 * </p>
 * Only String, int, long and double are supported as data type
 */
@Target(ElementType.PARAMETER) @Retention(RetentionPolicy.CLASS) @Documented
public @interface QueryParam {

  /**
   * The name of the query parameter. This one is optional. If not specified the variable name is used.
   */
  String value() default "";
}
