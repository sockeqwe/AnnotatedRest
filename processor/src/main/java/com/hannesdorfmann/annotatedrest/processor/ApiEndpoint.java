package com.hannesdorfmann.annotatedrest.processor;

import com.hannesdorfmann.annotatedrest.annotation.Api;
import java.util.HashMap;
import java.util.Map;
import javax.lang.model.element.ExecutableElement;

/**
 * @author Hannes Dorfmann
 */
public class ApiEndpoint {

  private Map<String, HttpMethod> getMethod = new HashMap<String, HttpMethod>();
  private Map<String, HttpMethod> postMethod = new HashMap<String, HttpMethod>();
  private Map<String, HttpMethod> putMethod = new HashMap<String, HttpMethod>();
  private Map<String, HttpMethod> deleteMethod = new HashMap<String, HttpMethod>();
  private Map<String, HttpMethod> headMethod = new HashMap<String, HttpMethod>();


  private String servletClassName;


  public ApiEndpoint(Api apiAnnotation) {
    servletClassName = apiAnnotation.servletClassName();
  }

  public String getServletClassName() {
    return servletClassName;
  }

  public void addHttpMethod(ExecutableElement methodElemet, Class<?> httpMethod) throws ProcessingException {

  }

}
