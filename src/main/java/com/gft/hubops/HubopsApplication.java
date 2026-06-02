package com.gft.hubops;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HubopsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HubopsApplication.class, args);
    }

}