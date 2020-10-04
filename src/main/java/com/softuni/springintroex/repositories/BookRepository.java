package com.softuni.springintroex.repositories;

import com.softuni.springintroex.entities.bookshop.Author;
import com.softuni.springintroex.entities.bookshop.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate date);

    List<Book> findAllByAuthorOrderByReleaseDateDescTitleAsc(Author author);
}
