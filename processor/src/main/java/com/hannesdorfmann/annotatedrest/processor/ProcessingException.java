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

  public ProcessingException(Element causeElement, String msg, Object... args) {
    super(String.format(msg, args));
    this.causeElement = causeElement;
  }

  public Element getCauseElement() {
    return causeElement;
  }
}
