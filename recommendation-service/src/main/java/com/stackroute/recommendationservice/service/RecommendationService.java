package com.stackroute.recommendationservice.service;

import com.stackroute.recommendationservice.model.Author;
import com.stackroute.recommendationservice.model.Book;
import com.stackroute.recommendationservice.model.User;

import java.util.List;

public interface RecommendationService {

    List<Book> generateRecommendations(String emailId);

    List<Book> getFavouritesList(String emailId);

    List<Book> retrieveAllBooks();

    void createBookAuthorRelationship(String bookTitle, String authorName);

    String createBookNode(Book book);

    String checkIfFavourite(String emailId, String bookTitle);

    String favouriteBook(String emailId, String bookTitle);

    String unFavouriteBook(String emailId, String bookTitle);

    String userStartedReadingBook(String emailId, String bookTitle);

    String increaseBookDownloads(String bookTitle);

    String increaseBookViews(String bookTitle);

    Book getABook(String bookTitle);

    List<Book> getInProgressBookList(String emailId);
}