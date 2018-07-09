package com.services.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;


@Data
public class Result {
  
  @JsonProperty("name")
  private String name;

  @JsonProperty("alpha2_code")
  private String alpha2Code;

  @JsonProperty("alpha3_code")
  private String alpha3Code;

  private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
