package com.hannesdorfmann.annotatedrest.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotated classes with this annotation to mark them as a API entry point.
 * Methods in this class can be annotated with @Get and other http methods. The idea is to specify a
 * base path and a version. Both are optional but strongly recommended. Take this example
 * <p>
 *  @Api(
 *        version = "v1",
 *        basePath = "/car"
 *  )
 *  public class CarApi {
 *
 *    @Get(
 *        path = "/{id}"
 *    )
 *    public Car getById(@Named("id") String id){
 *      return database.getCar(id);
 *    }
 *
 *    @Get (
 *          path = "/latest"
 *    )
 *    public void getLatest(){
 *      database.getLatestCar();
 *    }
 *
 *    @Post
 *    public Car createCar(Car car){
 *      // Store car in datastore
 *    }
 *
 *  }
 * </p>
 *
 * This generates the following urls / routes:
 * <ul>
 *   <li>Http GET: /v1/car/123 --> getById(123) </li>
 *   <li>Http GET: /v1/car/latest --> getLatest() </li>
 *   <li>Http POST: /v1/car --> createCar() </li>
 * </ul>
 *
 *
 * @author Hannes Dorfmann
 */
@Target(ElementType.TYPE) @Retention(RetentionPolicy.CLASS) @Documented
public @interface Api {

  /**
   * The version of the api. Strongly recommended to set this.
   * @return
   */
  String version() default "";

  /**
   * The base path of this api end point
   * @return
   */
  String basePath() default "";

  /**
   * The name of the servlet that will be generated for this and other @Api annotated classes.
   * The annotated class with the same servletClassName will be grouped together. Hence the path
   * to an http method (like @Get) must be unique
   * @return
   */
  String servletClassName() default "";
}
