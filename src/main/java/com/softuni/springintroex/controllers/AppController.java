package com.softuni.springintroex.controllers;

import com.softuni.springintroex.entities.bookshop.Author;
import com.softuni.springintroex.entities.bookshop.Book;
import com.softuni.springintroex.entities.usersystem.User;
import com.softuni.springintroex.io.interfaces.InputReader;
import com.softuni.springintroex.io.interfaces.OutputWriter;
import com.softuni.springintroex.services.AuthorService;
import com.softuni.springintroex.services.BookService;
import com.softuni.springintroex.services.CategoryService;
import com.softuni.springintroex.services.UserService;
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

    private final UserService userService;

    private final InputReader reader;
    private final OutputWriter writer;

    @Autowired
    public AppController(CategoryService categoryService,
                         AuthorService authorService,
                         BookService bookService,
                         UserService userService,
                         InputReader reader, OutputWriter writer) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.userService = userService;

        this.reader = reader;
        this.writer = writer;
    }


    @Override
    public void run(String... args) throws IOException, ParseException {
        // SEED DATA TO BOOKSHOP SYSTEM
//        this.categoryService.seedCategories();
//        this.writer.writeLine("Categories seeded in Database!");
//        this.authorService.seedAuthors();
//        this.writer.writeLine("Authors seeded in Database!");
//        this.bookService.seedBooks();
//        this.writer.writeLine("Books seeded in Database!");


        // BOOKSHOP SYSTEM QUERIES

        // 1. Get all books after the year 2000. Print only their titles.
        // this.getAllBooksAfterYear2000();


        // 2. Get all authors with at least one book with release date before 1990.
        // Print their first name and last name.
        // this.getAllAuthorsWithBooksReleaseDateBeforeYear();

        // 3. Get all authors, ordered by the number of their books (descending).
        // Print their first name, last name and book count.
        // this.getAllAuthorsOrderedByBooksCountDesc();


        // 4. Get all books from author George Powell,
        // ordered by their release date (descending),
        // then by book title (ascending).
        // Print the book's title, release date and copies.
        // this.getAllBooksFromAuthorOrderedByReleaseDateDescBookTitleAsc();


        // USERS SYSTEM

        // Register a new User in the System
        // this.registerNewUser();


        // BOOKSHOP SYSTEM Advanced Querying

        // Books Titles by Age Restriction
        // this.getAllBookTitlesByAgeRestriction();

        // Golden Books
        // this.getBooksByEditionTypeAndCopiesLessThan();

        // Books by Price
        // this.getBooksByPrice();

        // Not Released Books
        // this.getBooksNotReleasedInYear();

    }

    //  --- METHODS BOOKSHOP SYSTEM Advanced Querying  --- //

    // Not Released Books
    private void getBooksNotReleasedInYear() throws IOException {
        this.writer.writeLine("Enter Release Year: ");
        int year = Integer.parseInt(this.reader.readLine());

        this.bookService
                .getAllNotReleasedInYear(year)
                .forEach(b -> this.writer.writeLine(b.getTitle()));
    }

    // Books by Price
    private void getBooksByPrice() throws IOException {
        this.writer.writeLine("Enter Lower Price Bound: ");
        BigDecimal lower = new BigDecimal(this.reader.readLine());

        this.writer.writeLine("Enter Greater Price Bound: ");
        BigDecimal greater = new BigDecimal(this.reader.readLine());

        this.bookService
                .getAllByPriceLessThanOrGreaterThan(lower, greater)
                .forEach(b -> this.writer.writeLine(
                        String.format("%s - $%s", b.getTitle(), b.getPrice())));
    }

    // Golden Books
    private void getBooksByEditionTypeAndCopiesLessThan() throws IOException {
        this.writer.writeLine("Enter Edition Type [NORMAL, PROMO, GOLD]: ");
        String editionType = this.reader.readLine();
        this.writer.writeLine("Enter number of Copies: ");
        int copies = Integer.parseInt(this.reader.readLine());

        this.bookService
                .getAllByEditionTypeAndCopiesLessThan(editionType, copies)
                .forEach(b -> this.writer.writeLine(b.getTitle()));
    }

    // Books Titles by Age Restriction
    private void getAllBookTitlesByAgeRestriction() throws IOException {
        this.writer.writeLine("Enter Age Restriction: ");
        this.bookService.getAllBooksByAgeRestriction(this.reader.readLine())
                .forEach(b -> this.writer.writeLine(b.getTitle()));
    }

    //  --- END OF METHODS BOOKSHOP SYSTEM Advanced Querying  --- //

    //  --- METHODS BOOKSHOP SYSTEM  --- //

    // 1. Get all books after the year 2000.
    private void getAllBooksAfterYear2000() throws IOException {
        this.writer.writeLine("Enter a Book release year: ");
        int year = Integer.parseInt(reader.readLine());

        for (Book book : this.bookService.getAllBooksAfter(year)) {
            this.writer.writeLine(book.getTitle());
        }
    }

    // 2. Get all authors with at least one book with release date before 1990.
    private void getAllAuthorsWithBooksReleaseDateBeforeYear() throws IOException {

        this.writer.writeLine("Enter a Book release year: ");
        int year = Integer.parseInt(reader.readLine());

        Set<Author> authors = this.authorService.getAllAuthorsWithBooksBefore(year);

        for (Author author : authors) {
            this.writer
                    .writeLine(String.format("%s %s",
                            author.getFirstName(),
                            author.getLastName()));
        }
    }

    // 3. Get all authors, ordered by the number of their books (descending).
    private void getAllAuthorsOrderedByBooksCountDesc() {
        List<Author> allByBooksCount = this.authorService.getAllByBooksCount();

        allByBooksCount.forEach(author -> this.writer.writeLine(
                author.getFirstName() + " " +
                        author.getLastName() + " - " +
                        author.getBooks().size()));
    }

    // 4. Get all books from author George Powell
    private void getAllBooksFromAuthorOrderedByReleaseDateDescBookTitleAsc() {
        String authorName = "George Powell";
        Author author = this.authorService.getAuthorByName(authorName);

        List<Book> books = this.bookService.findBooksByAuthor(author);

        // OUTPUT
        this.writer.writeLine("-".repeat(80));
        this.writer.writeLine(String.format("All books by %s %s:",
                author.getFirstName(),
                author.getLastName()));
        this.writer.writeLine("-".repeat(80));

        for (Book book : books) {
            this.writer.writeLine(String.format("Title: \"%s\", Released on: %s. Copies sold: %d",
                    book.getTitle(),
                    book.getReleaseDate(),
                    book.getCopies()));
        }
    }

    //  --- END OF METHODS BOOKSHOP SYSTEM  --- //


    //  --- METHODS USERS SYSTEM  --- //

    private void registerNewUser() throws IOException {
        try {
            this.writer.writeLine("Enter Username: ");
            String username = this.reader.readLine();
            this.writer.writeLine("Enter Password: ");
            String password = this.reader.readLine();
            this.writer.writeLine("Enter your Email: ");
            String email = this.reader.readLine();
            this.writer.writeLine("Enter your Age: ");
            int age = Integer.parseInt(this.reader.readLine());

            User user = new User(username, password, email, LocalDateTime.now(), age);
            this.userService.registerUser(user);
            this.writer.writeLine(String.format("User %s registered succesfully!", username));
        } catch (ConstraintViolationException e) {
            this.writer.writeLine(e.getMessage());
        }
    }


}
