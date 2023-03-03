package com.jee.crud.controller;

import com.jee.crud.model.Author;
import com.jee.crud.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Author> getAuthorByName(@PathVariable String name) {
        Author author = authorRepository.findByName(name);
        if (author != null) {
            return ResponseEntity.ok().body(author);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
