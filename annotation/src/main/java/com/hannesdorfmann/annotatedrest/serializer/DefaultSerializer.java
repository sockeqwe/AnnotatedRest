package com.hannesdorfmann.annotatedrest.serializer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * The default serializer uses jackson for serializing / deserializing json objects
 * @author Hannes Dorfmann
 */
public class DefaultSerializer implements Serializer {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override public void serialize(Object value, OutputStream outputStream) throws Exception {

    objectMapper.writeValue(outputStream, value);

  }

  @Override public Object deserialize(InputStream inputStream, Class<?> target, boolean isList)
      throws Exception {
    if (isList){
      TypeFactory f = objectMapper.getTypeFactory();
      return objectMapper.readValue(inputStream, f.constructCollectionType(List.class, target));
    } else {
      return objectMapper.readValue(inputStream, target);
    }
  }

  @Override public String getContentType() {
    return null;
  }
}
