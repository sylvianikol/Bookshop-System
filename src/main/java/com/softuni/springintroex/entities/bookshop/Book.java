package com.softuni.springintroex.entities.bookshop;

import com.softuni.springintroex.entities.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
public class Book extends BaseEntity {

    private String title;
    private String description;
    private EditionType editionType;
    private BigDecimal price;
    private int copies;
    private LocalDate releaseDate;
    private AgeRestriction ageRestriction;

    private Set<Category> categories;
    private Author author;

    public Book() {
        this.setCategories(new HashSet<>());
    }

    public Book(String title, EditionType editionType,
                BigDecimal price, int copies,
                LocalDate releaseDate,
                AgeRestriction ageRestriction,
                Author author) {
        this.setTitle(title);
        this.setEditionType(editionType);
        this.setPrice(price);
        this.setCopies(copies);
        this.setReleaseDate(releaseDate);
        this.setAgeRestriction(ageRestriction);
        this.setAuthor(author);
    }

    @Column(nullable = false, length = 50)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(length = 1000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Enumerated // EnumType.ORDINAL is used by default
    // columnDefinition=smallint since we will not need more than 2 bytes
    @Column(name = "edition_type", columnDefinition = "smallint", nullable = false)
    public EditionType getEditionType() {
        return editionType;
    }

    public void setEditionType(EditionType editionType) {
        this.editionType = editionType;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(nullable = false)
    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }

    @Column(name = "release_date")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Enumerated // EnumType.ORDINAL is used by default
    // columnDefinition=smallint since we will not need more than 2 bytes
    @Column(name = "age_restriction", columnDefinition = "smallint", nullable = false)
    public AgeRestriction getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(AgeRestriction ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
