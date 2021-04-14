package com.stackroute.recommendationservice.service;

import com.stackroute.recommendationservice.model.Author;
import com.stackroute.recommendationservice.model.Book;
import com.stackroute.recommendationservice.model.User;
import com.stackroute.recommendationservice.repository.AuthorRepository;
import com.stackroute.recommendationservice.repository.BookRepository;
import com.stackroute.recommendationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/*main service class to manage user recommendations*/
@Service
public class RecommendationServiceImpl implements RecommendationService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private UserRepository userRepository;

    @Autowired
    public RecommendationServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
    }

    /*method to generate user book recommendations*/
    @Override
    public List<Book> generateRecommendations(String emailId) {
        return (List<Book>) this.userRepository.collaborativeFilteringRecommendations(emailId);
    }

    /*method to get user favourite books*/
    @Override
    public List<Book> getFavouritesList(String emailId) {
        return this.userRepository.userFavouriteBooks(emailId);
    }

    /*method to retrieve all books*/
    @Override
    public List<Book> retrieveAllBooks() {
        List<Book> books = (List<Book>) this.bookRepository.findAll();
        Collections.reverse(books);
        return books;
    }

    /*method to create relationship between author and book*/
    @Override
    public void createBookAuthorRelationship(String bookTitle, String authorName) {
        List<Author> existingAuthors = authorRepository.findAuthorsByAuthorName(authorName);
        if (existingAuthors.size() == 0) {
            Author bookAuthor = new Author(authorName);
            this.authorRepository.save(bookAuthor);
        }
        this.bookRepository.createAuthoredByRelationship(bookTitle, authorName);
    }

    /*method to create book node*/
    @Override
    public String createBookNode(Book book) {
        String bookTitle = book.getBookTitle();
        List<Book> books = bookRepository.findBooksByBookTitle(bookTitle);
        if (books.size() == 0) {
            this.bookRepository.save(book);
            String[] authorNames = book.getAuthorName().split(",");
            if (authorNames.length == 1) {
                createBookAuthorRelationship(bookTitle, book.getAuthorName());
            } else {
                for (String author : authorNames) {
                    createBookAuthorRelationship(bookTitle, author);
                }
            }
            return "Book Node created";
        } else {
            return "Book Already Exists in Database";
        }
    }

    /*method to check if book is favourite*/
    @Override
    public String checkIfFavourite(String emailId, String bookTitle) {
        String result = this.userRepository.checkIfFavourite(emailId, bookTitle);
        if (result != null && !result.isEmpty()) {
            return "Book is favourite";
        } else {
            return "Not favourite";
        }
    }

    /*method to mark book as favourite*/
    @Override
    public String favouriteBook(String emailId, String bookTitle) {
        List<User> users = this.userRepository.findUsersByEmailId(emailId);
        if (users.size() == 0) {
            User user = new User(emailId);
            this.userRepository.save(user);
        }
        this.userRepository.addBookAsFavourite(emailId, bookTitle);
        return "Book Added as Favourite";
    }

    /*method to unFavourite book */
    @Override
    public String unFavouriteBook(String emailId, String bookTitle) {
        this.userRepository.removeBookAsFavourite(emailId, bookTitle);
        return "Book Removed from Favourite";
    }

    /*method to create read relationship*/
    @Override
    public String userStartedReadingBook(String emailId, String bookTitle) {
        if (emailId != null && bookTitle != null) {
            List<User> users = this.userRepository.findUsersByEmailId(emailId);
            if (users.size() == 0) {
                User user = new User(emailId);
                this.userRepository.save(user);
            }
            String result = this.userRepository.checkInProgressStatus(emailId, bookTitle);
            if (result == null || result.isEmpty()) {
                this.userRepository.addReadRelationship(emailId, bookTitle);
            }
            return "User started reading book";
        }
        return "Field missing while creating read relationship";
    }

    /*method to increase book download count */
    @Override
    public String increaseBookDownloads(String bookTitle) {
        this.userRepository.increaseBookDownloads(bookTitle);
        return "Book downloads updated";
    }

    /*method to increase book view count */
    @Override
    public String increaseBookViews(String bookTitle) {
        this.userRepository.increaseBookViews(bookTitle);
        return "Book views updated";
    }

    /*method to get a book by title */
    @Override
    public Book getABook(String bookTitle) {
        return this.userRepository.getABook(bookTitle);
    }

    /*method to get users current reading books */
    @Override
    public List<Book> getInProgressBookList(String emailId) {
        return this.userRepository.userCurrentReadingBooks(emailId);
    }
}