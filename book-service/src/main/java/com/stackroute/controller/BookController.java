package com.stackroute.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.exception.BookNotFound;
import com.stackroute.model.Book;
import com.stackroute.model.CommentSections;
import com.stackroute.services.BookService;
import com.stackroute.services.RabbitMqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/*This is a Controller class , it will upload the book, retrieve the book and add comments to the book .
* this class is annotated with @RestController annotation, @RequestMapping */

@RestController
@CrossOrigin(value = "*")
@RequestMapping("api/v1/book")
public class BookController {

    private BookService bookService;
    private RabbitMqSender rabbitMqSender;

    /*The service class and rabbitmq sender is injected into the controller by @Autowired annotation .*/
    @Autowired
    public BookController(BookService bookService, RabbitMqSender rabbitMqSender) {
        this.bookService = bookService;
        this.rabbitMqSender = rabbitMqSender;
    }

    @Value("${app.message}")
    private String message;


    /*This method uploads the book given by the user.*/
    @PostMapping("/upload")
    public ResponseEntity<Book> uploadBook(@RequestParam(value = "file") MultipartFile file,
                                           @RequestParam(value = "image") MultipartFile image, @RequestParam("item") String item) throws IOException {

        Book bookObj = new ObjectMapper().readValue(item, Book.class);
        bookObj.setBookId(UUID.randomUUID().toString());
        bookObj.setFileByte(file.getBytes());
        bookObj.setFile(file.getOriginalFilename());
        bookObj.setImageByte(image.getBytes());
        bookObj.setImage(image.getOriginalFilename());
        bookObj.setType(image.getContentType());
        bookObj.setTotalViews(0L);
        bookObj.setTotalDownloads(0L);
        bookObj.setUploadedOn(new Date(System.currentTimeMillis()));
        String fileUrl= bookService.uploadFile(file);
        final String response = "[" + file.getOriginalFilename() + "] uploaded successfully.";
        bookObj.setBookUrl(fileUrl);
        Book uploadbook = bookService.uploadBook(bookObj);
        rabbitMqSender.send(uploadbook);
        rabbitMqSender.sendToRecommendation(uploadbook);


        return new ResponseEntity<>(uploadbook, HttpStatus.CREATED);
    }


    /*This method will provide the book path(url) of the required book based on the book title .*/
    @GetMapping("/bookpath/{bookTitle}")
    public ResponseEntity<String> getBookById(@PathVariable String bookTitle) throws BookNotFound {
        String bookDetails = bookService.getBookPath(bookTitle);
        return new ResponseEntity<String>(bookDetails, HttpStatus.OK);
    }

    /*This method will retrieve the book details based on the book title.*/
    @GetMapping("/bookdetails/{bookTitle}")
    public ResponseEntity<Book> getBookByTitle(@PathVariable String bookTitle) throws BookNotFound {
        Book bookDetails = bookService.getBookDetails(bookTitle);
        return new ResponseEntity<Book>(bookDetails, HttpStatus.OK);
    }

    /*This method will retrieve the list of uploaded books*/
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    /*This method is used to add comments to the book.*/
    @PutMapping("/addcomments/{bookTitle}")
    public void addComments(@PathVariable String bookTitle, @RequestBody CommentSections commentDetails) {
        System.out.println(bookTitle);
        System.out.println(commentDetails);
        bookService.updateComments(bookTitle, commentDetails);
    }
    @GetMapping("/download/{bookTitle}")
    public ResponseEntity<byte[]> getFile(@PathVariable String bookTitle) throws BookNotFound{
        Book fileDB = bookService.getBookDetails(bookTitle);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getFile() + "\"")
                .body(fileDB.getFileByte());
    }
}