package com.lind.basic.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class MyStartupRunner2 implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(MyStartupRunner1.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info("MyStartupRunner2里的数据");

    }
}
