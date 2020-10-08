package com.softuni.springintroex.services;

import com.softuni.springintroex.entities.bookshop.Category;

import java.io.IOException;
import java.util.Set;

public interface CategoryService {
    void seedCategories() throws IOException;

    Category getCategoryById(long id);

    long getCount();

    Set<Category> getRandomCategories();
}
