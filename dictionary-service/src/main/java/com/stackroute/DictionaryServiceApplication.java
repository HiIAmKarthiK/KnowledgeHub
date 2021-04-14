package com.stackroute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/*This is the main class with @EnableDiscoveryClient annotation. */
@SpringBootApplication
@EnableDiscoveryClient
public class DictionaryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DictionaryServiceApplication.class, args);
    }

}
