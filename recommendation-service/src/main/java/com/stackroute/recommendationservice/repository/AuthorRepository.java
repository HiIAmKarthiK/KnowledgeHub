package com.stackroute.recommendationservice.repository;

import com.stackroute.recommendationservice.model.Author;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*Repository to manage author related info*/
@Repository
public interface AuthorRepository extends Neo4jRepository<Author, Long> {

    /*Query to find the author by his name*/
    @Query("MATCH(a:Author {authorName:$authorName}) RETURN a")
    List<Author> findAuthorsByAuthorName(String authorName);
}