package com.lind.basic.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@SpringBootTest()
@EnableCaching
@RunWith(SpringJUnit4ClassRunner.class)
public class CacheTest {
  @Autowired
  CommonService commonService;

  @Test
  public void getCache() {
    // 不走缓存
    commonService.get("ok");
    // 添加缓存
    commonService.add("ok");
    // 这条从缓存读取
    commonService.get("ok");
    // 删除缓存
    commonService.delete("ok");
    // 不走缓存
    commonService.get("ok");

  }
}
