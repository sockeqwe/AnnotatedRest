package com.hannesdorfmann.exception;

/**
 * Http Status Code 409 excpetion
 * @author Hannes Dorfmann
 */
public class ConflictException extends HttpException {

  public ConflictException() {
    this("");
  }

  public ConflictException(String message) {
    super(message, 409);
  }
}
