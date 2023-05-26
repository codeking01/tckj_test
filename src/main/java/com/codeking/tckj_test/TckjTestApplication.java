package com.codeking.tckj_test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.codeking.tckj_test.mapper")
public class TckjTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TckjTestApplication.class, args);
    }

}
