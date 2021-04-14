package com.stackroute.recommendationservice.controller;


import com.stackroute.recommendationservice.model.Author;
import com.stackroute.recommendationservice.model.Book;
import com.stackroute.recommendationservice.model.User;
import com.stackroute.recommendationservice.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* This is a Controller class named Recommendation.
The class is annotated with @RestController, @RequestMapping, and @CrossOrigin annotations.
This class is responsible for creating recommendations for the user.
*/
@RestController
@RequestMapping("/api/v1/recommendation/")
@CrossOrigin(value = "*")
public class RecommendationController {
    private RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /*End point to GET LIST of books according to user recommendations*/
    @GetMapping("/recommendations")
    public ResponseEntity<List<Book>> generateRecommendations(@RequestParam(value = "emailId") String emailId) {
        return new ResponseEntity<List<Book>>(this.recommendationService.generateRecommendations(emailId), HttpStatus.OK);
    }

    /*End point to GET LIST of favourite books according to the user*/
    @GetMapping("/favourites")
    public ResponseEntity<List<Book>> getFavouritesList(@RequestParam(value = "emailId") String emailId) {
        return new ResponseEntity<List<Book>>(recommendationService.getFavouritesList(emailId), HttpStatus.OK);
    }

    /*End point to check if a book is favourite according to the user*/
    @GetMapping("/userfavourite")
    public ResponseEntity<String> checkIfFavourite(@RequestParam(value = "emailId") String emailId, @RequestParam(value = "bookTitle") String bookTitle) {
        return new ResponseEntity<String>(recommendationService.checkIfFavourite(emailId, bookTitle), HttpStatus.OK);
    }

    /*End point to GET all uploaded books*/
    @GetMapping("/books")
    public ResponseEntity<List<Book>> retrieveAllBooks() {
        return new ResponseEntity<List<Book>>(this.recommendationService.retrieveAllBooks(), HttpStatus.OK);
    }

    /*End point to create a book node on upload*/
    @PostMapping("/book")
    public ResponseEntity<String> createBookNode(@RequestBody Book book) {
        String message = this.recommendationService.createBookNode(book);
        return new ResponseEntity<String>(message, HttpStatus.CREATED);
    }

    /*End point to mark a book as user favourite*/
    @PostMapping("/favourite")
    public ResponseEntity<String> favouriteBook(@RequestBody String title, @RequestParam(value = "emailId") String emailId, @RequestParam(value = "bookTitle") String bookTitle) {
        String message = recommendationService.favouriteBook(emailId, bookTitle);
        return new ResponseEntity<String>(message, HttpStatus.CREATED);
    }

    /*End point to remove a book from favourites*/
    @GetMapping("/unfavourite")
    public ResponseEntity<String> unFavouriteBook(@RequestParam(value = "emailId") String emailId, @RequestParam(value = "bookTitle") String bookTitle) {
        String message = recommendationService.unFavouriteBook(emailId, bookTitle);
        return new ResponseEntity<String>(message, HttpStatus.CREATED);
    }

    /*End point to create a relationship called read when user reads*/
    @PostMapping("/startreading")
    public ResponseEntity<String> startBookReading(@RequestBody String title, @RequestParam(value = "emailId") String emailId, @RequestParam(value = "bookTitle") String bookTitle) {
        String message = recommendationService.userStartedReadingBook(emailId, bookTitle);
        return new ResponseEntity<String>(message, HttpStatus.CREATED);
    }

    /*End point to count downloads on book download*/
    @PutMapping("/updatedownloads")
    public ResponseEntity<String> updateBookDownloads(@RequestBody String bookTitle) {
        String message = recommendationService.increaseBookDownloads(bookTitle);
        return new ResponseEntity<String>(message, HttpStatus.ACCEPTED);
    }

    /*End point to count number of views on a book*/
    @PutMapping("/updateviews")
    public ResponseEntity<String> updateBookViews(@RequestBody String bookTitle) {
        System.out.println(bookTitle);
        String message = recommendationService.increaseBookViews(bookTitle);
        return new ResponseEntity<String>(message, HttpStatus.ACCEPTED);
    }

    /*End point to GET a particular book*/
    @GetMapping("/abook")
    public ResponseEntity<Book> getABook(@RequestParam String bookTitle) {
        return new ResponseEntity<>(this.recommendationService.getABook(bookTitle), HttpStatus.OK);
    }

    /*End point to GET all books with read relationship property in progress */
    @GetMapping("/reading")
    public ResponseEntity<List<Book>> getInProgressBookList(@RequestParam(value = "emailId") String emailId) {
        return new ResponseEntity<List<Book>>(recommendationService.getInProgressBookList(emailId), HttpStatus.OK);
    }

}