package com.softuni.springintroex.repositories;

import com.softuni.springintroex.entities.bookshop.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findCategoryByName(String name);

    Category findCategoryById(long id);
}
