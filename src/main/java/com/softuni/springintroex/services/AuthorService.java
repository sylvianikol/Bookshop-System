package com.softuni.springintroex.services;

import com.softuni.springintroex.entities.bookshop.Author;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface AuthorService {
    void seedAuthors() throws IOException;

    long getAuthorsCount();

    Author getAuthorById(long id);

    Set<Author> getAllAuthorsWithBooksBefore(int year);

    List<Author> getAllByBooksCount();

    Author getAuthorByName(String authorName);

    Author getRandomAuthor();

    List<Author> getAllByNameEndingWith(String s);
}
