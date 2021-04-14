package com.stackroute.searchservice.controller;

import com.stackroute.searchservice.exception.SearchBookNotFound;
import com.stackroute.searchservice.model.Book;
import com.stackroute.searchservice.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * This is the controller of the search service.
 */
@RestController
@CrossOrigin(value = "*")
@RequestMapping("/books")
public class BookController {

    /**
     * This is the constructor of the book controller that @Autowired the
     * BookRepository
     */
    @Autowired
    private BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * This method is used to insert the data in elastic search. The data are coming
     * from the Frontend.
     * 
     * @param book
     * @return
     * @throws Exception
     */
    @PostMapping
    public Book insertBook(@RequestBody Book book) throws Exception {
        return bookRepository.insertBook(book);
    }

    /**
     * This method is used to get all the books.
     * 
     * @return
     */
    @GetMapping("/allbooks")
    public Map<String, Object> getAllBook() {
        return bookRepository.getAllBook();
    }

    /**
     * This method is used to get the book related to the id which is passing in
     * this method.
     * 
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Map<String, Object> getBookById(@PathVariable String id) {
        return bookRepository.getBookById(id);
    }

    /**
     * This method is used to update the book related to the id which is passing in
     * this method.
     * 
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public Map<String, Object> updateBookById(@RequestBody Book book, @PathVariable String id) {
        return bookRepository.updateBookById(id, book);
    }

    /**
     * This method is used to delete the book related to the id which is passing in
     * this method.
     * 
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable String id) {
        bookRepository.deleteBookById(id);
    }

    /**
     * This method is used to get the books related to keyword which is passing in
     * this method.
     * 
     * @param queries
     * @return
     */
    @GetMapping("/search/{queries}")
    public List<Book> BookSearch(@PathVariable String queries) throws SearchBookNotFound {
        System.out.println("\n\n\nInside Book Service.Ths is what we have got from book query :  " + queries);
        String text = "";
        System.out.println("\n\nReturnning back to bookquery service");
        return bookRepository.findBook(queries);
    }
}
