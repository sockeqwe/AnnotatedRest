package com.hannesdorfmann.exception;

/**
 * Represents http 404 status code
 * @author Hannes Dorfmann
 */
public class NotFoundException extends HttpException {

  public NotFoundException() {
    this("");
  }

  public NotFoundException(String message) {
    super(message, 404);
  }
}
