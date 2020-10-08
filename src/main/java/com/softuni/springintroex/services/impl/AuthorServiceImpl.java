package com.softuni.springintroex.services.impl;

import com.softuni.springintroex.entities.bookshop.Author;
import com.softuni.springintroex.repositories.AuthorRepository;
import com.softuni.springintroex.services.AuthorService;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static com.softuni.springintroex.constants.GlobalConstants.*;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        List<String> fileContent = this.fileUtil.readFileContent(AUTHORS_FILE_PATH);

        for (String authorFullName : fileContent) {
            String[] names = authorFullName.split("\\s+");
            String firstName = names[0];
            String lastName = names[1];

            if (this.authorRepository
                    .findAuthorByFirstNameAndLastName(firstName, lastName) == null) {

                Author author = new Author(firstName, lastName);

                this.authorRepository.saveAndFlush(author);
            }
        }
    }

    @Override
    public long getAuthorsCount() {
        return this.authorRepository.count();
    }

    @Override
    public Author getAuthorById(long id) {
        return this.authorRepository.findAuthorById(id);
    }

    @Override
    public Set<Author> getAllAuthorsWithBooksBefore(int year) {
        return this.authorRepository
                .findAllByBooksBefore(LocalDate.of(year, 1, 1));
    }

    @Override
    public List<Author> getAllByBooksCount() {
        return this.authorRepository.findAllByBooksCount();
    }

    @Override
    public Author getAuthorByName(String authorName) {
        String[] names = authorName.split("\\s+");
        return this.authorRepository.findAuthorByFirstNameAndLastName(names[0], names[1]);
    }

    @Override
    public Author getRandomAuthor() {
        Random random = new Random();
        int randomId = random.nextInt((int) this.getAuthorsCount()) + 1;

        return this.getAuthorById(randomId);
    }

    @Override
    public List<Author> getAllByFirstNameEndingWith(String s) {
        return this.authorRepository.findAllByFirstNameEndingWith(s);
    }

    @Override
    public List<Author> getAllByLastNameStartingWith(String s) {
        return this.authorRepository
                .findAllByLastNameIgnoreCaseStartingWith(s);
    }

    @Override
    public List<Author> getAllByNumberOfBooksCopiesDesc() {
        return this.authorRepository
                .findAllByTotalBookCopiesOrderByTotalDesc();
    }
}
