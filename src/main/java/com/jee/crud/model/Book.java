package com.jee.crud.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "isbn")
    private Long isbn;

    @Column(name = "title", length = 200)
    private String title;

    @Column(name = "publication", length = 25)
    private String publication;

    @Column(name = "price")
    private int price;

    @ManyToOne
    @JoinColumn(name="author_id", referencedColumnName="id")
    private Author author;

    @Column(name = "genre", length = 100)
    private String genre;

    // constructeur par défaut
    public Book() {}

    public Book(Long isbn, String title, String publication, int price, Author author, String genre) {
        this.isbn = isbn;
        this.title = title;
        this.publication = publication;
        this.price = price;
        this.author = author;
        this.genre = genre;
    }

    // getters et setters

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
