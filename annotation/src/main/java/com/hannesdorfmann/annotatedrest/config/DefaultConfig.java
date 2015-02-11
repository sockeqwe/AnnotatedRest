package com.hannesdorfmann.annotatedrest.config;

import com.hannesdorfmann.annotatedrest.serializer.DefaultSerializer;
import com.hannesdorfmann.annotatedrest.serializer.Serializer;

/**
 * The default config
 *
 * @author Hannes Dorfmann
 */
public class DefaultConfig implements Configuration {

  @Override public Serializer getSerializer() {
    return new DefaultSerializer();
  }
}
