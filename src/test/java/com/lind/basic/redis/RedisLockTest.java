package com.lind.basic.redis;

import com.lind.basic.config.JedisLock;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("test")
@Slf4j
public class RedisLockTest {
    @Autowired
    JedisLock jedisLock;

    /**
     * 分布锁测试.
     */
    @Test
    public void distributeLock() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    Long generatorId = System.currentTimeMillis();
                    if (jedisLock.tryLock("user", generatorId.toString(), 100)) {
                        logger.info("{}线程拿到了锁", Thread.currentThread().getName());
                        Thread.sleep(90);
                        jedisLock.releaseLock("user", generatorId.toString());
                    }
                }
            };
        }
        Thread.sleep(5000);
    }
}
