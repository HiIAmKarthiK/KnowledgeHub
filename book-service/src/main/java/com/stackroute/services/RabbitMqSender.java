package com.stackroute.services;

import com.stackroute.model.Book;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/*this is the rabbit mq sender class will help to share the data and connect to other microservices*/
@Service
public class RabbitMqSender {
    private RabbitTemplate rabbitTemplate;

    /*The RabbitTemplate is injected, which accepts and forwards the messages*/
    @Autowired
    public RabbitMqSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingkey;

    @Value("${spring.rabbitmq.recommendationexchange}")
    private String exchange2;

    @Value("${spring.rabbitmq.recommendationroutingkey}")
    private String routingkey2;

    public void send(Book book) {
        rabbitTemplate.convertAndSend(exchange, routingkey, book);
    }

    public void sendToRecommendation(Book book){
        rabbitTemplate.convertAndSend(exchange2,routingkey2,book);
    }
}