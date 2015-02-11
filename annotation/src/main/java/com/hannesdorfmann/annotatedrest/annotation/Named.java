package com.hannesdorfmann.annotatedrest.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to identify the url parameters like this:
 * <p>
 * <code>
 * @Api
 * public class ThingApi {
 *   @Get(
 *        path = "/thing/{id}/foo/{other}
 *   )
 *   public Thing getThing(@Named int id, @Named("other") String o){
 *     ...
 *   }
 * }
 * </code>
 * </p>
 *
 * Only String, int, long and double are supported as data type
 * @author Hannes Dorfmann
 */
@Target(ElementType.PARAMETER) @Retention(RetentionPolicy.CLASS) @Documented
public @interface Named {

  /**
   * The name of the parameter, which must match the <i>{VariableName}</i> specified in path.
   * This one is optional. If not specified the variable name is used.
   * @return
   */
  String value();
}
