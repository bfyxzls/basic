package com.lind.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(exclude = {
        ElasticsearchAutoConfiguration.class //不对es进行自动增配，目前没有es环境
})
@EnableAsync
public class BasicApplication {

    static int hello() {
        int a = 1;
        int b = 2;
        int c = a + b;
        return c;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(hello());
        SpringApplication.run(BasicApplication.class, args);
    }
}
