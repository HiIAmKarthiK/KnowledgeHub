//package com.stackroute.recommendationservice.service;
//
//import com.stackroute.recommendationservice.model.Author;
//import com.stackroute.recommendationservice.model.Book;
//import com.stackroute.recommendationservice.repository.AuthorRepository;
//import com.stackroute.recommendationservice.repository.BookRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Arrays;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class RecommendationServiceTest {
//
//    @Autowired
//    Book book;
//    Author author;
//
//    @Mock
//    BookRepository bookRepository;
//    AuthorRepository authorRepository;
//
//
//    @InjectMocks
//    RecommendationServiceImpl recommendationService;
//
//    @BeforeEach
//    public void setUp() {
//        author = new Author();
//        author.setAuthorName("JK Rowling");
//        book = new Book();
//        book.setBookTitle("Harry Potter");
//        book.setAuthorName("JK Rowling");
//        book.setBookUrl("http");
//        book.setImageUrl("http");
//        book.setDescription("book by jk rowling");
//        book.setLanguage("English");
//        book.setIsbnNumber("yes");
//        book.setPublisher("mpp");
//        book.setTotalDownloads(100L);
//        book.setTotalViews(100L);
//        book.setBookGenre(new String[] {"Thriller","Drama"});
//        book.setTotalPage(100);
//
//
//
//    }
//
//    @Test
//    public void ShouldCreateBookNode() {
//        when(bookRepository.save(book)).thenReturn(book);
//        when(authorRepository.save(any())).thenReturn(author);
//        String response = recommendationService.createBookNode(book);
//    }
//
//}