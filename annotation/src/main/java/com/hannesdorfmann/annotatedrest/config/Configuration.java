package com.hannesdorfmann.annotatedrest.config;

import com.hannesdorfmann.annotatedrest.serializer.Serializer;

/**
 * @author Hannes Dorfmann
 */
public interface Configuration {

  public Serializer getSerializer();

}
