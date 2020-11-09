package com.softuni.springintroex.controllers;

import com.softuni.springintroex.entities.bookshop.Author;
import com.softuni.springintroex.entities.bookshop.Book;
import com.softuni.springintroex.io.interfaces.InputReader;
import com.softuni.springintroex.io.interfaces.OutputWriter;
import com.softuni.springintroex.services.AuthorService;
import com.softuni.springintroex.services.BookService;
import com.softuni.springintroex.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Controller
public class AppController implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    private final InputReader reader;
    private final OutputWriter writer;

    @Autowired
    public AppController(CategoryService categoryService,
                         AuthorService authorService,
                         BookService bookService,
                         InputReader reader,
                         OutputWriter writer) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;

        this.reader = reader;
        this.writer = writer;
    }


    @Override
    public void run(String... args) throws IOException, ParseException {

        // SEED DATABASE
//        this.seedBookshopDatabase();

        // 1. Books Titles by Age Restriction
//         this.getAllBookTitlesByAgeRestriction();

        // 2. Golden Books
//         this.getBooksByEditionTypeAndCopiesLessThan();

        // 3. Books by Price
//         this.getBooksByPrice();

        // 4. Not Released Books
//         this.getBooksNotReleasedInYear();

        // 5. Books Released Before Date
//         this.getBooksReleasedBefore();

        // 6. Authors Search
//         this.getAuthorsWithNameEndingWith();

        // 7. Books Search
//         this.getBooksByTitleContaining();

        // 8. Book Titles Search
//         this.getBooksByAuthorsLastNameStartingWith();

        // 9. Count Books
//         this.countBooksByTitleLengthGreaterThan();

        // 10. Total Book Copies
//         this.getTotalNumberOfBookCopiesByAuthor();

        // 11. Reduced Book
//         this.getReducedBookInformation();

        // 12. * Increase Book Copies
//         this.increaseBookCopies();

        // 13. * Remove Books
//         this.removeBooksWithCopiesLessThan();

        // 14. * Stored Procedure
//        this.getTotalBooksPerAuthor();

    }


    //  --- METHODS  --- //

    // 14. * Stored Procedure
    private void getTotalBooksPerAuthor() throws IOException {
        this.writer.writeLine("Enter Author's Full Name: ");
        String fullName = this.reader.readLine();

        int count = this.bookService.getTotalBooksCountPerAuthor(fullName);

        this.writer.writeLine(String.format("%s has written %d books", fullName, count));
    }

    // 13. * Remove Books
    private void removeBooksWithCopiesLessThan() throws IOException {
        this.writer.writeLine("Enter a number: ");
        int number = Integer.parseInt(this.reader.readLine());

        int result = this.bookService.removeBooksWithCopiesLessThan(number);
        this.writer.writeLine(result);
    }

    // 12. * Increase Book Copies
    private void increaseBookCopies() throws IOException {
        this.writer.writeLine("Enter a Date (dd MMM yyyy): ");
        String date = this.reader.readLine();
        this.writer.writeLine("Enter a Number of Copies: ");
        int copies = Integer.parseInt(this.reader.readLine());

        int updated = this.bookService.increaseCopies(date, copies);

        this.writer.writeLine(copies * updated);
    }

    // 11. Reduced Book
    private void getReducedBookInformation() throws IOException {
        this.writer.writeLine("Enter Book Title: ");
        String title = this.reader.readLine();

        Book book = this.bookService.getBookInfo(title);

        this.writer.writeLine(String.format("%s %s %s %.2f",
                book.getTitle(),
                book.getEditionType(),
                book.getAgeRestriction(),
                book.getPrice()));

    }

    // 10. Total Book Copies
    private void getTotalNumberOfBookCopiesByAuthor() {
        this.writer.writeLine("-".repeat(38));
        this.writer.writeLine("Total number of book copies by author:");
        this.writer.writeLine("-".repeat(38));

        this.authorService
                .getAllByNumberOfBooksCopiesDesc()
                .forEach(a -> this.writer.writeLine(String.format("%s - %d",
                        a.getFullName(),
                        this.bookService.getTotalCopiesPerAuthor(a))));
    }

    // 9. Count Books
    private void countBooksByTitleLengthGreaterThan() throws IOException {
        this.writer.writeLine("Enter length of title: ");
        int length = Integer.parseInt(this.reader.readLine());

        this.writer.writeLine(String.format("There are %d books with title longer than %d symbols",
                this.bookService.countBooksByTitleLengthGreaterThan(length), length));
    }

    // 8. Book Titles Search
    private void getBooksByAuthorsLastNameStartingWith() throws IOException {
        this.writer.writeLine("Enter a string: ");
        String s = this.reader.readLine();

        this.bookService.getAllBooksByAuthorsNameStartingWith(s)
                .forEach(b -> this.writer.writeLine(b.getTitle()));
    }

    // 7. Books Search
    private void getBooksByTitleContaining() throws IOException {
        this.writer.writeLine("Enter a string: ");
        String s = this.reader.readLine();

        this.bookService
                .getAllBooksByTitleContaining(s)
                .forEach(b -> this.writer.writeLine(b.getTitle()));
    }

    // 6. Authors Search
    private void getAuthorsWithNameEndingWith() throws IOException {
        this.writer.writeLine("Enter a string: ");
        String s = this.reader.readLine();

        this.authorService
                .getAllByFirstNameEndingWith(s)
                .forEach(a -> this.writer.writeLine(a.getFullName()));
    }

    // 5. Books Released Before Date
    private void getBooksReleasedBefore() throws IOException {
        this.writer.writeLine("Enter Release Date in format [dd-MM-yyyy]: ");
        String date = this.reader.readLine();

        this.bookService
                .getAllBooksReleasedBefore(date)
                .forEach(b -> this.writer.writeLine(
                        String.format("%s %s %s",
                                b.getTitle(),
                                b.getEditionType().toString(),
                                b.getPrice())
                        )
                );
    }

    // 4. Not Released Books
    private void getBooksNotReleasedInYear() throws IOException {
        this.writer.writeLine("Enter Release Year: ");
        int year = Integer.parseInt(this.reader.readLine());

        this.bookService
                .getAllNotReleasedInYear(year)
                .forEach(b -> this.writer.writeLine(b.getTitle()));
    }

    // 3. Books by Price
    private void getBooksByPrice() throws IOException {
        this.writer.writeLine("-".repeat(50));
        this.writer.writeLine("Books with price lower than 5 and higher than 40:");
        this.writer.writeLine("-".repeat(50));

        BigDecimal lower = new BigDecimal("5");
        BigDecimal greater = new BigDecimal("40");

        this.bookService
                .getAllByPriceLessThanOrGreaterThan(lower, greater)
                .forEach(b -> this.writer.writeLine(
                        String.format("%s - $%s", b.getTitle(), b.getPrice())));
    }

    // 2. Golden Books
    private void getBooksByEditionTypeAndCopiesLessThan() throws IOException {
        this.writer.writeLine("-".repeat(40));
        this.writer.writeLine("Golden Books with less than 5000 copies: ");
        this.writer.writeLine("-".repeat(40));

        String editionType = "GOLD";
        int copies = 5000;

        this.bookService
                .getAllByEditionTypeAndCopiesLessThan(editionType, copies)
                .forEach(b -> this.writer.writeLine(b.getTitle()));
    }

    // 1. Books Titles by Age Restriction
    private void getAllBookTitlesByAgeRestriction() throws IOException {
        this.writer.writeLine("Enter Age Restriction: ");
        this.bookService.getAllBooksByAgeRestriction(this.reader.readLine())
                .forEach(b -> this.writer.writeLine(b.getTitle()));
    }

    private void seedBookshopDatabase() throws IOException, ParseException {
        this.categoryService.seedCategories();
        this.writer.writeLine("Categories seeded in Database!");
        this.authorService.seedAuthors();
        this.writer.writeLine("Authors seeded in Database!");
        this.bookService.seedBooks();
        this.writer.writeLine("Books seeded in Database!");

        this.writer.writeLine("DONE!");
    }

    //  --- END OF METHODS BOOKSHOP SYSTEM Advanced Querying  --- //

}
