package com.stackroute.services;

import com.stackroute.exception.BookNotFound;
import com.stackroute.model.Book;
import com.stackroute.model.CommentSections;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

/*This is the service class, where the abstract methods are declared. */
public interface BookService {
    /* This method will upload the book*/
    Book uploadBook(Book book);

    /* This method will find the book*/
    Book findBook(String bookTitle);

    /* This method will find the book details by title*/
    Book getBookDetails(String bookTitle) throws BookNotFound;

    /* This method will  find the book and get the book Url path*/
    String getBookPath(String bookTitle) throws BookNotFound;

    /* This method will update the comments given to the book*/
    Book updateComments(String bookTitle, CommentSections commentSections);

    /* This method will List all the books */
    List<Book> getAllBooks();

    String uploadFile(MultipartFile multipartFile);
    
}