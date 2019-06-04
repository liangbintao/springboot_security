package com.mochu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.mochu.mapper")
@EnableScheduling
@SpringBootApplication
public class XueHuaMatrixApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(XueHuaMatrixApplication.class, args);
        ConfigurableEnvironment environment = context.getEnvironment();

        Systems.init(environment);
    }

}
