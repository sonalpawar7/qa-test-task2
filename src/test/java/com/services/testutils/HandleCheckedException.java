package com.services.testutils;


public interface HandleCheckedException<T> {
  T getException() throws Exception;
}