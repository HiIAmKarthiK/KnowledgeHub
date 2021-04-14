package com.stackroute.recommendationservice.repository;

import com.stackroute.recommendationservice.model.Book;
import com.stackroute.recommendationservice.model.User;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

/*Repository to manage all Users*/
@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {

    /*Query to return list of books based on user recommendations*/
    @Query("MATCH(u1:User {emailId:$emailId})-[:read]->(:Book)<-[:read]-(u2:User)-[:read]->(b:Book)  WHERE u1<>u2 AND NOT (u1)-[:read]->(b)  WITH b,count(b) as frequency  ORDER BY frequency DESC  RETURN  b")
    List<Book> collaborativeFilteringRecommendations(String emailId);

    /*Query to return list of user favourite books*/
    @Query("MATCH(u:User {emailId:$emailId})-[:favourite]->(b:Book) RETURN b")
    List<Book> userFavouriteBooks(String emailId);

    /*Query to check if the book is favourite*/
    @Query("MATCH(u:User {emailId:$emailId})-[r]->(b:Book {bookTitle:$bookTitle}) WHERE type(r)='favourite' RETURN 'Yes'")
    String checkIfFavourite(String emailId, String bookTitle);

    /*Query to find user by his email*/
    @Query("MATCH(u:User {emailId:$emailId}) RETURN u")
    List<User> findUsersByEmailId(String emailId);

    /*Query to add book to favourites*/
    @Query("MATCH(u:User {emailId:$emailId}),(b:Book {bookTitle:$bookTitle}) CREATE(u)-[:favourite]->(b)")
    void addBookAsFavourite(String emailId, String bookTitle);

    /*Query to remove book from favourites*/
    @Query("MATCH(u:User {emailId:$emailId})-[r:favourite]->(b:Book {bookTitle:$bookTitle}) DELETE r")
    void removeBookAsFavourite(String emailId, String bookTitle);

    /*Query to add read relationship between book and user*/
    @Query("OPTIONAL MATCH(u:User {emailId:$emailId}),(b:Book {bookTitle:$bookTitle}) CREATE (u)-[r:read {status:'in_progress'}]->(b)")
    void addReadRelationship(String emailId, String bookTitle);

    /*Query to increase book downloads*/
    @Query("MATCH(b:Book {bookTitle:$bookTitle}) SET b.totalDownloads=b.totalDownloads+1")
    void increaseBookDownloads(String bookTitle);

    /*Query to check in status as in progress*/
    @Query("MATCH(u:User {emailId:$emailId})-[:read {status:'in_progress'}]->(b:Book {bookTitle:$bookTitle}) RETURN 'Yes'")
    String checkInProgressStatus(String emailId, String bookTitle);

    /*Query to increase book views*/
    @Query("MATCH(b:Book {bookTitle:$bookTitle}) SET b.totalViews=b.totalViews+1")
    void increaseBookViews(String bookTitle);

    /*Query to get a book by title*/
    @Query("MATCH (b:Book {bookTitle:$bookTitle}) RETURN b")
    Book getABook(String bookTitle);

    /*Query to get users current reading books*/
    @Query("MATCH(u:User {emailId:$emailId})-[:read {status:'in_progress'}]->(b:Book) RETURN b")
    List<Book> userCurrentReadingBooks(String emailId);
}