package com.lind.basic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.Charsets;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseTest {

  protected MockMvc mockMvc;
  @Autowired
  protected ObjectMapper objectMapper;
  @Autowired
  private WebApplicationContext webApplicationContext;

  /**
   * 获取测试资源文件.
   *
   * @param name 文件名
   */
  public static File getResource(String name) {
    ClassLoader classLoader = BaseTest.class.getClassLoader();
    File file = new File(classLoader.getResource(name).getFile());
    return file;
  }

  @Before
  public void setUp() throws Exception {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  /**
   * 获取文件流.
   *
   * @param path 文件路径
   * @return
   */
  public String getStringFromJson(String path, Charset charset) throws IOException {
    return Resources.toString(Resources.getResource(path), charset);
  }

  /**
   * 将json转换为对象.
   *
   * @param path 文件路径
   */
  public <T> T fromJson(String path, Class<T> cls) throws IOException {
    return objectMapper
        .readValue(this.getStringFromJson(path, Charsets.toCharset("UTF-8")), cls);
  }

  /**
   * 将json数组转换为对象列表.
   *
   * @param path 文件路径
   */
  public <T> List<T> fromJsonArray(String path, TypeReference typeReference) throws IOException {
    return objectMapper
        .readValue(getStringFromJson(path, Charsets.toCharset("UTF-8")), typeReference);
  }

  /**
   * 循环执行线程.
   */
  protected void periodicCheck(PassableRunnable runnable) throws InterruptedException {
    while (true) {
      runnable.run();
      if (runnable.isPassed()) {
        return;
      }
      TimeUnit.MILLISECONDS.sleep(50);
    }
  }

  public interface PassableRunnable extends Runnable {

    boolean isPassed();
  }
}
