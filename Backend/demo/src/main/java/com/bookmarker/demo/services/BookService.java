package com.bookmarker.demo.services;

import com.bookmarker.demo.entities.Book;
import com.bookmarker.demo.exception.BookNotFoundException;
import com.bookmarker.demo.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService{
    private final BookRepo bookRepo;

    @Autowired
    public BookService(BookRepo bookRepo){
        this.bookRepo = bookRepo;
    }

    public Book addBook(Book book){
        return bookRepo.save(book);
    }

    public List<Book> findAllBooks(){
        return bookRepo.findAll();
    }

    public Book updateBook(Book book){
        Optional<Book> to_be_updated = bookRepo.findBookById(book.getId());
        if(to_be_updated.isEmpty()) throw new BookNotFoundException("Book doesn't exists");
        else{
            if(book.getTitle() != null) to_be_updated.get().setTitle(book.getTitle());
            if(book.getAuthor() != null) to_be_updated.get().setAuthor(book.getAuthor());
            if(book.getDescription() != null) to_be_updated.get().setDescription(book.getDescription());
            if(book.getPublisher() != null) to_be_updated.get().setPublisher(book.getPublisher());
            if(book.getDate_published() != null) to_be_updated.get().setDate_published(book.getDate_published());
            bookRepo.save(to_be_updated.get());
        }
        return to_be_updated.get();
    }

    public Book changeBookReadStatus(Long id){
        Optional<Book> book = bookRepo.findBookById(id);
        if(book.isPresent()){
            book.get().setBook_read(!book.get().getBook_read());
            bookRepo.save(book.get());
            return book.get();
        }else{
            throw new BookNotFoundException("Book with id " + id + " not found.");
        }
    }

    public Book findBookById(Long id){
        return bookRepo.findBookById(id).orElseThrow(()-> new BookNotFoundException("Book with id " + id + " doesn't exist"));
    }

    public List<Book> getToBeReadBooks(){
        List<Book> all_books = bookRepo.findAll();
        all_books.removeIf(book -> book.getBook_read());
        return all_books;
    }

    public List<Book> getAlreadyReadBooks(){
        List<Book> all_books = bookRepo.findAll();
        all_books.removeIf(book -> !book.getBook_read());
        return all_books;
    }

    public void deleteBook(Long id){
        bookRepo.deleteBookById(id);
    }

}
