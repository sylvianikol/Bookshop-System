package com.softuni.springintroex.services.impl;

import com.softuni.springintroex.entities.bookshop.Category;
import com.softuni.springintroex.repositories.CategoryRepository;
import com.softuni.springintroex.services.CategoryService;
import com.softuni.springintroex.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static com.softuni.springintroex.constants.GlobalConstants.CATEGORIES_FILE_PATH;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedCategories() throws IOException {


        String[] fileContent =
                this.fileUtil.readFileContent(CATEGORIES_FILE_PATH);


        for (String categoryName : fileContent) {
            if (this.categoryRepository.findCategoryByName(categoryName) == null) {
                Category category = new Category(categoryName);

                this.categoryRepository.saveAndFlush(category);
            }
        }
    }

    @Override
    public Category getCategoryById(long id) {
        return this.categoryRepository.findCategoryById(id);
    }

    @Override
    public long getCount() {
        return this.categoryRepository.count();
    }

    @Override
    public Set<Category> getRandomCategories() {
        Random random = new Random();

        Set<Category> categories = new HashSet<>();

        for (int i = 0; i < 3; i++) {
            int randomId = random.nextInt((int) this.getCount()) + 1;
            categories.add(this.getCategoryById(randomId));
        }
        return categories;
    }

}
