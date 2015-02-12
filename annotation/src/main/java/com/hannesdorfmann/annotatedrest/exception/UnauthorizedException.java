package com.hannesdorfmann.annotatedrest.exception;

/**
 * Http Status Code 401 excpetion
 * @author Hannes Dorfmann
 */
public class UnauthorizedException extends HttpException {

  public UnauthorizedException() {
    this("");
  }

  public UnauthorizedException(String message) {
    super(message, 401);
  }
}
