package com.stackroute.searchservice.service;

import com.stackroute.searchservice.model.Book;
import com.stackroute.searchservice.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This is the services of the Rabbitmq. Here its function is to get the data
 * through RabbitMQ producer. This is used as a RabbitMQ Consumer.
 */
@Service
public class RabbitMqService implements RabbitListenerConfigurer {

    private static Logger logger = LoggerFactory.getLogger(RabbitMqService.class);

    private BookRepository bookRepository;

    @Autowired
    public RabbitMqService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * It passes the queries and insert book details via bookRepository.
     * 
     * @param book
     */
    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(Book book) {

        logger.info("Book is: " + book);
        bookRepository.insertBook(book);

    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    }
}
