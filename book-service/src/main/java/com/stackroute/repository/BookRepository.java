package com.stackroute.repository;

import com.stackroute.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/*This is a repository class, which manages the data using MongoRepository.*/
@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    /*This method is used to get the given book  by the book title*/
    public Book findFirstByBookTitle(String bookTitle);
}
