package com.codvision.terminal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@ComponentScan(basePackages = "com.codvision")
//@MapperScan(value = "com.codvision.server.dao")
public class DemoSpringBoot {
    public static void main(String[] args) {
        SpringApplication.run(DemoSpringBoot.class, args);
    }
}
