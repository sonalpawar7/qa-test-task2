package com.services.api;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.services.testutils.TestUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ServicesApiTest {

  private static final Logger LOGGER = LoggerFactory.getLogger(ServicesApiTest.class);

  private HttpClient httpClient;

  private HttpGet httpGet;

  private HttpPost httpPost;

  private HttpResponse httpResponse;

  @Before
  public void initialization() {
    httpClient = HttpClientBuilder.create().build();
  }

  @Test
  public void getServicesTest() {
    String uri = "http://services.groupkt.com/country/get/all";
    httpGet = new HttpGet(uri);
    try {
      httpResponse = httpClient.execute(httpGet);
      StringBuffer sbResponse = readResponse(httpResponse);
      ResponseResource response =
          TestUtils.jsonToObject(sbResponse.toString(), ResponseResource.class);
      List<Result> countries = response.getRestResponse().getResult();
      Iterator<Result> it = countries.iterator();
      Set<String> set = new HashSet<String>();
      while (it.hasNext()) {
        set.add(it.next().getAlpha2Code());
      }
      assertThat(set.contains("US"), is(true));
      assertThat(set.contains("DE"), is(true));
      assertThat(set.contains("GB"), is(true));
      assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
  }

  /**
   * @param httpResponse
   * @return
   * @throws IOException
   */
  public StringBuffer readResponse(HttpResponse httpResponse) throws IOException {
    BufferedReader bufferedReader =
        new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
    StringBuffer sbResponse = new StringBuffer();
    String line = null;
    while ((line = bufferedReader.readLine()) != null) {
      sbResponse.append(line);
    }
    return sbResponse;
  }

  @Test
  public void getCountryWiseDetailsTest() {
    String uri = "http://services.groupkt.com/country/get/iso2code/";
    Set<String> set = new HashSet<String>();
    set.add("US");
    set.add("DE");
    set.add("GB");
    String country = null;
    Iterator<String> it = set.iterator();
    while (it.hasNext()) {
      country = (it.next());
      httpGet = new HttpGet(uri + country);
      try {
        httpResponse = httpClient.execute(httpGet);
        StringBuffer sbResponse = readResponse(httpResponse);
        assertTrue(sbResponse.toString().contains(country));
        assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
      } catch (Exception e) {
        LOGGER.error(e.getMessage());
      }
    }
  }

  @Test
  public void getInExistentCountryDetails() {
    String uri = "http://services.groupkt.com/country/get/all";
    // Considering GR as in existent country.
    String country = "GR";
    httpGet = new HttpGet(uri);
    try {
      httpResponse = httpClient.execute(httpGet);
      StringBuffer sbResponse = readResponse(httpResponse);
      assertTrue(sbResponse.toString().contains(country));
      assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
  }

  @Test
  public void postSampleCountryDetails() throws Exception {
    // Assuming following is post URI.
    String uri = "http://services.groupkt.com/country/post";
    httpPost = new HttpPost(uri);
    Result request = new Result();
    request.setName("Test Country");
    request.setAlpha2Code("TC");
    request.setAlpha3Code("TCY");
    String payload = TestUtils.objectToJson(request);
    HttpEntity httpEntity = new StringEntity(payload);
    httpPost.setEntity(httpEntity);
    try {
      httpResponse = httpClient.execute(httpPost);
      assertEquals(httpResponse.getStatusLine().getStatusCode(), 200);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
  }
}