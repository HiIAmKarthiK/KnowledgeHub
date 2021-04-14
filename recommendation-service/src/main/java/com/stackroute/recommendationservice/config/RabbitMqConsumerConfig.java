package com.stackroute.recommendationservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/* RabbitMQ config class*/
@Configuration
public class RabbitMqConsumerConfig {

    @Value("${spring.rabbitmq.queue2}")
    private String queue2;

    @Value("${spring.rabbitmq.exchange2}")
    private String exchange2;

    @Value("${spring.rabbitmq.routingkey2}")
    private String routingkey2;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Bean
    Queue queue2() {
        return new Queue(queue2, true);
    }

    @Bean
    Exchange exchange2() {
        return ExchangeBuilder.directExchange(exchange2).durable(true).build();
    }

    @Bean
    Binding binding2() {
        return BindingBuilder.bind(queue2())
                .to(exchange2())
                .with(routingkey2)
                .noargs();
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

}