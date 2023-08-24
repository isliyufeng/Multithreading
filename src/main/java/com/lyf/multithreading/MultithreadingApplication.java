package com.lyf.multithreading;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author joyous
 */
@SpringBootApplication
@MapperScan("com.lyf.multithreading.mapper")
public class MultithreadingApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultithreadingApplication.class, args);
    }

}
