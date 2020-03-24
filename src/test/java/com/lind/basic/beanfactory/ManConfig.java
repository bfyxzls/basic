package com.lind.basic.beanfactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ManConfig {
    @Bean
    public Man man() {
        Man man = new Man();
        man.setName("zzl");
        return man;
    }
}
