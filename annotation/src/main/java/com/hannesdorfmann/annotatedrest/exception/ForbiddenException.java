package com.hannesdorfmann.annotatedrest.exception;

/**
 * Http Status Code 403 excpetion
 * @author Hannes Dorfmann
 */
public class ForbiddenException extends HttpException {

  public ForbiddenException() {
    this("");
  }

  public ForbiddenException(String message) {
    super(message, 403);
  }
}
