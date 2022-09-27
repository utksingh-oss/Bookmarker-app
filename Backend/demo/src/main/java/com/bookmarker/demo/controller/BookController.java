package com.bookmarker.demo.controller;


import com.bookmarker.demo.entities.Book;
import com.bookmarker.demo.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController{
    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> all_books = bookService.findAllBooks();
        return new ResponseEntity<>(all_books , HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") Long id){
        Book book = bookService.findBookById(id);
        return new ResponseEntity<>(book , HttpStatus.OK);
    }

    @GetMapping("/unread_books")
    public ResponseEntity<List<Book>> getUnreadBooks(){
        List<Book> books = bookService.getToBeReadBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/to_be_read_books")
    public ResponseEntity<List<Book>> getReadBooks(){
        List<Book> books = bookService.getAlreadyReadBooks();
        return new ResponseEntity<>(books ,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        bookService.addBook(book);
        return new ResponseEntity<>(book , HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity<Book> updateBook(@RequestBody Book book){
        bookService.updateBook(book);
        return new ResponseEntity<>(book , HttpStatus.ACCEPTED);
    }

    @PutMapping("/change_status/{id}")
    public ResponseEntity<Book> changeBookStatus(@PathVariable("id") Long id){
        Book book = bookService.changeBookReadStatus(id);
        return new ResponseEntity<>(book , HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") Long id){
        bookService.deleteBook(id);
        return new ResponseEntity<>("book with id " + id + " deleted" , HttpStatus.OK);
    }
}