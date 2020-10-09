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
        {
//        this.categoryService.seedCategories();
//        this.writer.writeLine("Categories seeded in Database!");
//        this.authorService.seedAuthors();
//        this.writer.writeLine("Authors seeded in Database!");
//        this.bookService.seedBooks();
//        this.writer.writeLine("Books seeded in Database!");
       }

        // BOOKSHOP SYSTEM QUERIES Exercises
        {
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

        }

        // USERS SYSTEM Exercise

        // Register a new User in the System
        // this.registerNewUser();


        // BOOKSHOP SYSTEM Advanced Querying Exercises

        // 1. Books Titles by Age Restriction
        // this.getAllBookTitlesByAgeRestriction();

        // 2. Golden Books
        // this.getBooksByEditionTypeAndCopiesLessThan();

        // 3. Books by Price
        // this.getBooksByPrice();

        // 4. Not Released Books
        // this.getBooksNotReleasedInYear();

        // 5. Books Released Before Date
        // this.getBooksReleasedBefore();

        // 6. Authors Search
        // this.getAuthorsWithNameEndingWith();

        // 7. Books Search
        // this.getBooksByTitleContaining();

        // 8. Book Titles Search
        // this.getBooksByAuthorsLastNameStartingWith();

        // 9. Count Books
        // this.countBooksByTitleLengthGreaterThan();

        // 10. Total Book Copies
        // this.getTotalNumberOfBookCopiesByAuthor();

        // 11. Reduced Book
        // this.getReducedBookInformation();

        // 12. * Increase Book Copies
        // this.increaseBookCopies();

        // 13. * Remove Books
        // this.removeBooksWithCopiesLessThan();

        // 14. * Stored Procedure
        this.getTotalBooksPerAuthor();
    }

    //  --- METHODS BOOKSHOP SYSTEM Advanced Querying  --- //

    // 14. * Stored Procedure
    private void getTotalBooksPerAuthor() throws IOException {
        this.writer.writeLine("Enter Author's Full Name: ");
        String fullname = this.reader.readLine();

        int count = this.bookService.getTotalBooksCountPerAuthor(fullname);

        System.out.println(count);
    }

    // 13. * Remove Books
    private void removeBooksWithCopiesLessThan() throws IOException {
        this.writer.writeLine("Enter a number: ");
        int number = Integer.parseInt(this.reader.readLine());

        this.writer.writeLine(this.bookService.removeBooksWithCopiesLessThan(number));
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
        this.writer.writeLine("Enter Lower Price Bound: ");
        BigDecimal lower = new BigDecimal(this.reader.readLine());

        this.writer.writeLine("Enter Greater Price Bound: ");
        BigDecimal greater = new BigDecimal(this.reader.readLine());

        this.bookService
                .getAllByPriceLessThanOrGreaterThan(lower, greater)
                .forEach(b -> this.writer.writeLine(
                        String.format("%s - $%s", b.getTitle(), b.getPrice())));
    }

    // 2. Golden Books
    private void getBooksByEditionTypeAndCopiesLessThan() throws IOException {
        this.writer.writeLine("Enter Edition Type [NORMAL, PROMO, GOLD]: ");
        String editionType = this.reader.readLine();
        this.writer.writeLine("Enter number of Copies: ");
        int copies = Integer.parseInt(this.reader.readLine());

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
