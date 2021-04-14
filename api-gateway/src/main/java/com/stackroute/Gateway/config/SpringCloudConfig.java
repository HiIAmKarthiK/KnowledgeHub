package com.stackroute.Gateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@Configuration
public class SpringCloudConfig {
    @Bean
    public RouteLocator gateway(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r->r.path("/api/v1/login/**").uri("http://localhost:8086/"))

                .route(r->r.path("/api/v1/book/**").uri("http://localhost:8088/"))

                .route(r->r.path("/api/v1/dictionary/**").uri("http://localhost:8089/"))

                .route(r->r.path("/api/v1/recommendation/**").uri("http://localhost:9191/"))

                .route(r->r.path("/api/v1/register/**").uri("http://localhost:8082/"))

                .route(r->r.path("/books/**").uri("http://localhost:8087/"))

                .build();

    }

}