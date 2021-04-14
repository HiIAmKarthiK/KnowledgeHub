package com.stackroute.recommendationservice.service;


import com.stackroute.recommendationservice.model.Book;
import com.stackroute.recommendationservice.repository.BookRepository;
import com.stackroute.recommendationservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* Rabbit MQ listener class.*/
@Service
public class ConsumerService implements RabbitListenerConfigurer {

    private static Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    private UserRepository userRepository;
    private BookRepository bookRepository;
    private RecommendationService recommendationService;

    @Autowired
    public ConsumerService(UserRepository userRepository, BookRepository bookRepository, RecommendationService recommendationService) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.recommendationService = recommendationService;
    }

    /* save book in book repo*/
    @RabbitListener(queues = "${spring.rabbitmq.queue2}")
    public void receivedMessageBook(Book book) {

        logger.info("Book is: " + book);
        recommendationService.createBookNode(book);

    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    }
}