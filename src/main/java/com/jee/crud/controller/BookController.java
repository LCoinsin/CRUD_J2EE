package com.jee.crud.controller;

import com.jee.crud.model.Author;
import com.jee.crud.model.Book;
import com.jee.crud.repository.AuthorRepository;
import com.jee.crud.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookById(@PathVariable(value = "isbn") Long id) {
        Optional<Book> book = bookRepository.findById(String.valueOf(id));
        if(book.isPresent()) {
            return ResponseEntity.ok().body(book.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam(value = "title", required = false) String title,
                                  @RequestParam(value = "author", required = false) String authorName,
                                  @RequestParam(value = "price", required = false) Integer price) {
        List<Book> books = null;
        if(title != null && authorName != null && price != null) {
            Author author = authorRepository.findByName(authorName);
            books = bookRepository.findByTitleAndAuthorAndPrice(title, author, price);
        } else if(title != null && authorName != null) {
            Author author = authorRepository.findByName(authorName);
            books = bookRepository.findByTitleAndAuthor(title, author);
        } else if(title != null && price != null) {
            books = bookRepository.findByTitleAndPrice(title, price);
        } else if(authorName != null && price != null) {
            Author author = authorRepository.findByName(authorName);
            books = bookRepository.findByAuthorAndPrice(author, price);
        } else if(title != null) {
            books = bookRepository.findByTitle(title);
        } else if(authorName != null) {
            Author author = authorRepository.findByName(authorName);
            books = bookRepository.findByAuthor(author);
        } else if(price != null) {
            books = bookRepository.findByPrice(price);
        }
        return books;
    }

}
