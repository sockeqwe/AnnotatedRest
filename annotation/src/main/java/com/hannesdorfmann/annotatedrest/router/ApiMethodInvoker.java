package com.hannesdorfmann.annotatedrest.router;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Hannes Dorfmann
 */
public interface ApiMethodInvoker {

  public boolean matches(String url);

  /**
   * Dispatches the route by calling the corresponding method. if the method return nothing (because
   * the method is defined to returning void), then null will be returned.
   *
   * @throws Exception
   */
  public Object dispatch(HttpServletRequest request, HttpServletResponse response,
      Object requestBody) throws Exception;

  /**
   * Return the class type which of which the http requests body data is. The serializer will use
   * this class information to deserilize the http request body.
   *
   * @return the class or null if no parsing should be done
   */
  public Class<?> getRequestBodyType();

  /**
   * Extra flag to deal with the scenario where the http request body is a list (like json array).
   */
  public boolean isRequestBodyTypeList();
}
