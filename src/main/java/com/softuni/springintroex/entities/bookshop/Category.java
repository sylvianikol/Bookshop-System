package com.softuni.springintroex.entities.bookshop;

import com.softuni.springintroex.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {

    private String name;

    private Set<Book> books;

    public Category() {
        this.setBooks(new HashSet<>());
    }

    public Category(String name) {
        this.setName(name);
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "categories", targetEntity = Book.class)
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
