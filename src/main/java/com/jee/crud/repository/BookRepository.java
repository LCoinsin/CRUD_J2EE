package com.jee.crud.repository;

import com.jee.crud.model.Author;
import com.jee.crud.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByTitle(String title);
    List<Book> findByAuthor(Author author);
    List<Book> findByPrice(Integer price);
    List<Book> findByTitleAndAuthor(String title, Author author);
    List<Book> findByAuthorAndPrice(Author author, Integer price);
    List<Book> findByTitleAndPrice(String title, Integer price);
    List<Book> findByTitleAndAuthorAndPrice(String title, Author author, Integer price);
}
