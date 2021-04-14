package com.stackroute;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@EnableRabbit
/*This is the main class with rabbit mq consumer */
@SpringBootApplication
public class BookServiceApplication {

    @Value("${spring.rabbitmq.host}")
    String host;

    @Value("${spring.rabbitmq.username}")
    String userName;

    @Value("${spring.rabbitmq.password}")
    String password;

    /*A Bean with name ConnectionFactory is created, that helps in setting up a connection to rabbitmq server
    and configure binding to send the messages */
    @Bean
    public CachingConnectionFactory factory() {
        CachingConnectionFactory factory = new CachingConnectionFactory(host);
        factory.setUsername(userName);
        factory.setPassword(password);
        return factory;
    }

    /*A Bean with name MessageConverter is created, which converts java objects to JSON Format.*/
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /*A Bean with name RabbitTemplate is created, which accepts and forwards the messages*/
    @Bean
    public RabbitTemplate template(ConnectionFactory factory) {
        final RabbitTemplate template = new RabbitTemplate(factory);
        template.setMessageConverter(messageConverter());
        return template;
    }

   // private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(BookServiceApplication.class, args);
//        LOGGER.info("SpringbootS3tutorial application started successfully.");
    }
}

