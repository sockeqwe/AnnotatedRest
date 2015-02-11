package com.hannesdorfmann.annotatedrest.serializer;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * A serializer is, like the name suggests, responsible to serialize a java object to the desired
 * representation (like json) and vice versa.
 * <p><b>Don't close any output stream. It will be closed internally by the framework</b></p>
 *
 * @author Hannes Dorfmann
 */
public interface Serializer {

  /**
   * Called to serialize the server response (will be send as http response body)
   *
   * @throws Exception
   */
  public void serialize(Object value, OutputStream outputStream) throws Exception;

  /**
   * Called to deserialize the http body from a http request.
   *
   * @param target the class where to parse into. If a Collection like List is expected, than the
   * generic type will be here returend and the parameter isList will be set to true
   * @param isList true if a list should be deserialized
   * @throws Exception
   */
  public Object deserialize(InputStream inputStream, Class<?> target, boolean isList)
      throws Exception;

  /**
   * Get the mime type that can be serialized / deserialized with this Serializer.
   * <b>Not that this will also be used for the http response content type header</b>
   */
  public String getContentType();
}
