package com.av.domain;

import java.util.HashSet;
import java.util.Set;

public class Book {

    private Long id;
    private Genre genre;
    private Set<Author> authors;
    private String title;
    private int publishYear;
    private String isbn;
    private int edition;

    public Book() {
        authors = new HashSet<Author>();
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return (
            "Book{" +
            "id=" +
            id +
            ", genre=" +
            genre.toString() +
            ", authors=" +
            authors.toString() +
            ", title='" +
            title +
            '\'' +
            ", publishYear=" +
            publishYear +
            ", isbn='" +
            isbn +
            '\'' +
            ", edition=" +
            edition +
            '}'
        );
    }
}
