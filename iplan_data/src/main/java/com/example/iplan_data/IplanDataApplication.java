package com.example.iplan_data;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@MapperScan("com.example.iplan_data.mapper")
@SpringBootApplication
public class IplanDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(IplanDataApplication.class, args);
    }

}
