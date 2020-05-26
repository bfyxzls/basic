package com.lind.basic.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 项目启动后自动运行的代码CommandLineRunner
 */
@Component
@Order(1)
public class MyStartupRunner1 implements CommandLineRunner {
    private Logger logger = LoggerFactory.getLogger(MyStartupRunner1.class);

    @Override
    public void run(String... args) throws Exception {
        logger.info("MyStartupRunner1里的数据");
    }
}
