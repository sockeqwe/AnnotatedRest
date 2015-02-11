package com.hannesdorfmann.exception;

/**
 * Http Status Code 400 excpetion
 * @author Hannes Dorfmann
 */
public class BadRequestException extends HttpException {

  public BadRequestException() {
    this("");
  }

  public BadRequestException(String message) {
    super(message, 400);
  }
}
