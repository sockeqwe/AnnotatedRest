package com.hannesdorfmann.annotatedrest.processor;

import javax.lang.model.element.ExecutableElement;

/**
 * @author Hannes Dorfmann
 */
public class HttpMethod {

  private String httpMethod;
  private Class<?> returnType;
  private String methodName;
  private String simplifiedEqualityPath;

  private ExecutableElement methodElement;


  public HttpMethod(ExecutableElement methodElement){
    this.methodElement = methodElement;

  }


}
