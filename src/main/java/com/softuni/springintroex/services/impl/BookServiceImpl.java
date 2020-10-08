package com.softuni.springintroex.services.impl;

import com.softuni.springintroex.entities.bookshop.*;
import com.softuni.springintroex.repositories.BookRepository;
import com.softuni.springintroex.services.AuthorService;
import com.softuni.springintroex.services.BookService;
import com.softuni.springintroex.services.CategoryService;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static com.softuni.springintroex.constants.GlobalConstants.BOOKS_FILE_PATH;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    // One service -> One Repository (BookService -> BookRepository)
    private final BookRepository bookRepository;
    // If data is needed from other repositories inject their corresponding service instead!
    private final AuthorService authorService;
    private final CategoryService categoryService;

    private final FileUtil fileUtil;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository,
                           AuthorService authorService,
                           CategoryService categoryService,
                           FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedBooks() throws IOException {
        List<String> fileContent = this.fileUtil.readFileContent(BOOKS_FILE_PATH);

        for (String bookData : fileContent) {
            String[] params = bookData.split("\\s+");

            Author author = this.authorService.getRandomAuthor();
            EditionType editionType = EditionType.values()[Integer.parseInt(params[0])];

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate releaseDate = LocalDate.parse(params[1], formatter);

            int copies = Integer.parseInt(params[2]);
            BigDecimal price = new BigDecimal(params[3]);
            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(params[4])];
            String title = this.parseBookTitle(params);

            Book book = new Book(title, editionType, price, copies, releaseDate, ageRestriction, author);

            book.setCategories(this.categoryService.getRandomCategories());

            this.bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<Book> getAllBooksAfter(int year) {
        return this.bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<Book> findBooksByAuthor(Author author) {
        return this.bookRepository
                .findAllByAuthorOrderByReleaseDateDescTitleAsc(author);
    }

    @Override
    public List<Book> getAllBooksByAgeRestriction(String ageRestriction) {
        return this.bookRepository
                .findAllByAgeRestriction(AgeRestriction.valueOf(ageRestriction.toUpperCase()));
    }

    @Override
    public List<Book> getAllByEditionTypeAndCopiesLessThan(String editionType, int copies) {
        return this.bookRepository
                .findAllByEditionTypeAndCopiesIsLessThan(
                        EditionType.valueOf(editionType.toUpperCase()), copies);
    }

    @Override
    public List<Book> getAllByPriceLessThanOrGreaterThan(BigDecimal lessThan, BigDecimal greaterThan) {

        return this.bookRepository
                .findAllByPriceLessThanOrPriceGreaterThan(lessThan, greaterThan);
    }

    @Override
    public List<Book> getAllNotReleasedInYear(int year) {
        LocalDate before = LocalDate.of(year, 1, 1);
        LocalDate after = LocalDate.of(year, 12, 31);

        return this.bookRepository
                .findAllByReleaseDateBeforeOrReleaseDateAfter(before, after);
    }

    @Override
    public List<Book> getAllBooksReleasedBefore(String date) {
        int[] parts = Arrays.stream(date.split("-"))
                .mapToInt(Integer::parseInt)
                .toArray();

        LocalDate releaseDate = LocalDate.of(parts[2], parts[1], parts[0]);

        return this.bookRepository
                .findAllByReleaseDateBefore(releaseDate);
    }

    @Override
    public List<Book> getAllBooksByTitleContaining(String s) {
        return this.bookRepository.findAllByTitleIgnoreCaseContaining(s);
    }

    @Override
    public List<Book> getAllBooksByAuthorsNameStartingWith(String s) {
        List<Author> authors = this.authorService.getAllByLastNameStartingWith(s);
        return this.bookRepository.findAllByAuthorIn(authors);
    }

    @Override
    public int countBooksByTitleLengthGreaterThan(int length) {
        return this.bookRepository
                .countAllByTitleWithLengthGreaterThan(length);
    }

    //  HELPER METHODS  ////////////////////////////////
    private String parseBookTitle(String[] params) {
        StringBuilder titleBuilder = new StringBuilder();

        for (int i = 5; i < params.length; i++) {
            titleBuilder.append(params[i])
                    .append(" ");
        }

        return titleBuilder.toString().trim();
    }
}
