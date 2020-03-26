package com.lind.basic.nosql;

import com.lind.basic.config.JedisLock;
import com.lind.basic.config.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest()
@Profile("test")
public class RedisTest {

    private static final String key = "geo";
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JedisLock redisLock;
    @Autowired
    private RedisConfig redisConfig;

    @Test
    public void setTest() throws Exception {

        redisTemplate.opsForValue().set("ok", "test");
        System.out.println(
                "setTest:" + redisTemplate.opsForValue().get("ok")
        );
    }

    @Test
    public void hello() {
        Assert.assertEquals(1, 1);
    }

    /**
     * 方法强制为同步方法.
     */
    void queue() {
        String value = String.valueOf(System.currentTimeMillis() + 5);
        if (redisLock.tryLock("product", "1")) {
            System.out.println("printMessage synchronized result:" + LocalDateTime.now().toString()
                    + ",id:"
                    + Thread.currentThread().getId());
            redisLock.releaseLock("product", "1");
        }
    }

    @Test
    public void distributeLock() throws Exception {
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                queue();
            }
        }).start();
    }

    void queue2() {
        if (redisLock.tryLock("lind", "test")) {
            for (int i = 0; i < 5; i++) {
                System.out.println("printMessage synchronized result:" + LocalDateTime.now().toString()
                        + ",id:"
                        + Thread.currentThread().getId());
            }
            redisLock.releaseLock("lind", "test");
        }
    }

    /**
     * 代金卷例子.
     * set结构保证了value的唯一性.
     */
    @Test
    public void setCoupon() {
        final String coupon = "coupon";

        for (int i = 0; i < 100; i++) {
            redisTemplate.opsForSet().add(coupon, String.format("abc%s", i));
            redisTemplate.opsForSet().add(coupon, String.format("abc%s", i));
        }
        Assert.assertEquals(Long.valueOf(100), redisTemplate.opsForSet().size(coupon));
        redisTemplate.opsForSet().pop(coupon);
        Assert.assertEquals(Long.valueOf(99), redisTemplate.opsForSet().size(coupon));
    }

    /**
     * 用户消费top10.
     * sortList结构做实时排名.
     */
    @Test
    public void sortListTop() {
        final String consumption = "consumption";

        redisTemplate.opsForZSet().add(consumption, "person1", 1);
        redisTemplate.opsForZSet().add(consumption, "person2", 2);
        redisTemplate.opsForZSet().add(consumption, "person3", 1);

        for (Object o : redisTemplate.opsForZSet().rangeByScore(consumption, 1, 1)) {
            System.out.println(o);
        }

    }

    @Test
    public void distributeLock2() {

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                queue2();
            }
        }).start();
    }

    /**
     * 查看用户在线状态情况1在线，0离线.
     */
    @Test
    public void bitmapTest() {
        final String onlineKey = "online:";
        for (int i = 0; i < 100; i++) {
            redisTemplate.opsForValue().setBit(onlineKey, i, i % 2 == 0);
        }
        for (int i = 0; i < 10; i++) {

            System.out.println(i + "=" + redisTemplate.opsForValue().getBit(onlineKey, i));
        }
        System.out.println("online:" + redisConfig.bitCount(onlineKey));
    }

    /**
     * 统一数组里数据唯一性.
     */
    @Test
    public void hyperLogLogTest() {
        final String loglogKey = "loglog:";

        String[] arr = new String[100];
        for (int i = 0; i < 100; i++) {
            arr[i] = "A" + new Random().nextInt(10) + 1;
        }
        redisTemplate.opsForHyperLogLog().add(loglogKey, arr);
        System.out.println("loglog:" + redisTemplate.opsForHyperLogLog().size(loglogKey));
    }

    @Test
    public void inc() {
        Long count = 0L;
        if (redisTemplate.hasKey("okvalue")) {
            count = Long.valueOf(redisTemplate.opsForValue().get("okvalue").toString());
        } else {
            redisTemplate.opsForValue().set("okvalue", "0");
        }
        redisTemplate.opsForValue().increment("okvalue", 1);
        redisTemplate.opsForValue().increment("okvalue", 1);
        count = Long.valueOf(redisTemplate.opsForValue().get("okvalue").toString());
        System.out.println("okvalue=" + count);
    }
}
