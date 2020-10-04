package com.softuni.springintroex.services;

import com.softuni.springintroex.entities.bookshop.Category;

import java.io.IOException;

public interface CategoryService {
    void seedCategories() throws IOException;

    Category getCategoryById(long id);

    long getCount();
}
