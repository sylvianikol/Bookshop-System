package com.softuni.springintroex.repositories;

import com.softuni.springintroex.entities.bookshop.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findAuthorByFirstNameAndLastName(String firstName, String lastName);

    Author findAuthorById(long id);

    //Set<Author> findAllByBooksAfter(LocalDate date);

    @Query("SELECT a FROM Author a ORDER BY a.books.size DESC")
    List<Author> findAllByBooksCount();

    @Query("SELECT a FROM Author a LEFT JOIN a.books b WHERE b.releaseDate < ?1")
    Set<Author> findAllByBooksBefore(LocalDate date);

    List<Author> findAllByFirstNameEndingWith(String s);

    List<Author> findAllByLastNameIgnoreCaseStartingWith(String s);

    @Query(value = "SELECT * FROM `authors` `a`\n" +
            "JOIN `books` `b` ON `a`.`id` = `b`.`author_id`\n" +
            "GROUP BY `a`.`id`\n" +
            "ORDER BY sum(`b`.`copies`) DESC", nativeQuery = true)
    List<Author> findAllByTotalBookCopiesOrderByTotalDesc();


}
