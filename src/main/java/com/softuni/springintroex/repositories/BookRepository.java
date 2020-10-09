package com.softuni.springintroex.repositories;

import com.softuni.springintroex.entities.bookshop.AgeRestriction;
import com.softuni.springintroex.entities.bookshop.Author;
import com.softuni.springintroex.entities.bookshop.Book;
import com.softuni.springintroex.entities.bookshop.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate date);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByAuthorOrderByReleaseDateDescTitleAsc(Author author);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesIsLessThan(EditionType editionType, int copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal lessThan, BigDecimal greaterThan);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate before, LocalDate after);

    List<Book> findAllByTitleIgnoreCaseContaining(String s);

    List<Book> findAllByAuthorIn(List<Author> authors);

    @Query("SELECT count(b) FROM Book b WHERE length(b.title) > :length")
    int countAllByTitleWithLengthGreaterThan(@Param(value = "length") int length);

    @Query("SELECT sum(b.copies) FROM Book b WHERE b.author.id = :id")
    int getTotalCopiesPerAuthor(@Param(value = "id") long id);
}
