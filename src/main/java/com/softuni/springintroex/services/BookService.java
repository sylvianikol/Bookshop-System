package com.softuni.springintroex.services;

import com.softuni.springintroex.entities.bookshop.Author;
import com.softuni.springintroex.entities.bookshop.Book;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException, ParseException;

    List<Book> getAllBooksAfter(int year);

    List<Book> findBooksByAuthor(Author author);

    List<Book> getAllBooksByAgeRestriction(String ageRestriction);

    List<Book> getAllByEditionTypeAndCopiesLessThan(String editionType, int copies);

    List<Book> getAllByPriceLessThanOrGreaterThan(BigDecimal lessThan, BigDecimal greaterThan);
}
