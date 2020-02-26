package com.lind.basic.es;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.elasticsearch.common.collect.Lists;
import org.junit.After;
import org.junit.Before;

public class MockElasticsearchServerBase {
  private final String TEST_INDEX = "test-index";
  private final String TEST_TYPE = "document";
  private final ObjectMapper mapper = new ObjectMapper();
  private MockElasticsearchServer server;

  @Before
  public void setup() {
    server = new MockElasticsearchServer("data-directory", Lists.newArrayList(TEST_INDEX));

  }

  @After
  public void tearDown() throws IOException {
    server.shutdown();
  }

}
