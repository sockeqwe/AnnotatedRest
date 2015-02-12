package com.hannesdorfmann.annotatedrest.processor;

import javax.lang.model.element.Element;

/**
 * An exception that contains all information to print a annotation processor error message via
 * Messager
 *
 * @author Hannes Dorfmann
 */
public class ProcessingException extends Exception {

  String message;
  Element causeElement;
  Object[] args;

  public ProcessingException(Element causeElement, String msg, Object... args) {

    this.message = msg;
    this.causeElement = causeElement;
    this.args = args;
  }

  @Override public String getMessage() {
    return message;
  }

  public Element getCauseElement() {
    return causeElement;
  }

  public Object[] getMessageArgs() {
    return args;
  }
}
