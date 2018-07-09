package com.services.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ResponseResource {

  @JsonProperty("RestResponse")
  private RestResponse restResponse;

  private Map<String, Object> additionalProperties = new HashMap<String, Object>();
  
}