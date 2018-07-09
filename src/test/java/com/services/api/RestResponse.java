package com.services.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;


@Data
public class RestResponse {

  @JsonProperty("messages")
  private List<String> messages;
  
  @JsonProperty("result")
  private List<Result> result;

  private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
