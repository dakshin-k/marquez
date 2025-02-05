/*
 * Copyright 2018-2022 contributors to the Marquez project
 * SPDX-License-Identifier: Apache-2.0
 */

package marquez.client;

import java.net.MalformedURLException;
import java.net.URL;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@org.junit.jupiter.api.Tag("UnitTests")
public class MarquezUrlTest {
  static String basePath = "http://marquez:5000";
  static MarquezUrl marquezUrl;

  @BeforeAll
  static void beforeAll() throws MalformedURLException {
    marquezUrl = MarquezUrl.create(Utils.toUrl(basePath));
  }

  @Test
  void testBasicMarquezUrl() {
    URL url = marquezUrl.from("/namespace/nname/job/jname");
    Assertions.assertEquals("http://marquez:5000/namespace/nname/job/jname", url.toString());
  }

  @Test
  void testEncodedMarquezUrl() {
    URL url = marquezUrl.from("/namespace/s3:%2F%2Fbucket/job/jname");
    Assertions.assertEquals(
        "http://marquez:5000/namespace/s3:%2F%2Fbucket/job/jname", url.toString());
  }

  @Test
  void testToColumnLineageUrl() {
    Assertions.assertEquals(
        "http://marquez:5000/api/v1/column-lineage?nodeId=dataset%3Anamespace%3Adataset&depth=20&withDownstream=true",
        marquezUrl.toColumnLineageUrl("namespace", "dataset", 20, true).toString());

    Assertions.assertEquals(
        "http://marquez:5000/api/v1/column-lineage?nodeId=datasetField%3Anamespace%3Adataset%3Afield&depth=20&withDownstream=true",
        marquezUrl.toColumnLineageUrl("namespace", "dataset", "field", 20, true).toString());
  }
}
