package org.librehealth.fhir.platform.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.librehealth.fhir.platform.config.JacksonConfig;

import java.io.IOException;

public final class ConvertUtils {
  private static final ConvertUtils utils = new ConvertUtils();

  private ConvertUtils() {
  }

  public static ConvertUtils getInstance() {
    return utils;
  }

  public String toString(Object object) {
    try {
      return JacksonConfig.mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      return "";
    }
  }

  public <T> T fromString(String value, Class<T> aClass) {
    try {
      return JacksonConfig.mapper.readValue(value, aClass);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public <T> T fromString(String value, TypeReference<T> typeReference) {
    try {
      return JacksonConfig.mapper.readValue(value, typeReference);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }
}
