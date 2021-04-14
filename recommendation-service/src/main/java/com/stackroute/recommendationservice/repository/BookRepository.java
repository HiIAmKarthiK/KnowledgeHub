package com.stackroute.recommendationservice.repository;

import com.stackroute.recommendationservice.model.Book;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*Repository to manage all books*/
@Repository
public interface BookRepository extends Neo4jRepository<Book, Long> {

    /*Query to find the Book by its title*/
    @Query("MATCH(b:Book {bookTitle:$bookTitle}) RETURN b")
    List<Book> findBooksByBookTitle(String bookTitle);

    /*Query to create author and book relationship*/
    @Query("MATCH(b:Book {bookTitle:$bookTitle}),(a:Author {authorName:$authorName}) CREATE(b)-[:authored_by]->(a)")
    void createAuthoredByRelationship(String bookTitle, String authorName);

}