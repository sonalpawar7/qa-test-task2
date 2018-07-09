package com.services.testutils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class TestUtils {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
  }

  public static String objectToJson(Object object) {
    return supressException(() -> objectMapper.writeValueAsString(object));
  }

  @SuppressWarnings("unchecked")
  public static <T> T jsonToObject(String json, Class<?> clazz) {
    return supressException(() -> (T) objectMapper.readValue(json, clazz));
  }

  /**
   * Suppresses a checked Exception thrown by a method.
   */
  public static <T> T supressException(HandleCheckedException<T> throwException) {
    try {
      return throwException.getException();
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}