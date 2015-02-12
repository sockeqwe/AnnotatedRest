package com.hannesdorfmann.annotatedrest.exception;

/**
 * The basic class for Exception
 * @author Hannes Dorfmann
 */
public class HttpException extends Exception {

  private  int statusCode;

  public HttpException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

}
