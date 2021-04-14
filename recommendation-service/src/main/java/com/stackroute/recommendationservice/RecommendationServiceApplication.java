package com.stackroute.recommendationservice;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/* main function to start RecommendationServiceApplication */
@SpringBootApplication
//@EnableDiscoveryClient
@EnableRabbit
public class RecommendationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecommendationServiceApplication.class, args);
    }
}