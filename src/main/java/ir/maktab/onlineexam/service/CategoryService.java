package ir.maktab.onlineexam.service;

import ir.maktab.onlineexam.model.entity.Category;
import ir.maktab.onlineexam.model.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public Category findCategoryByName(String categoryName) {
        Optional<Category> found = categoryRepository.findByName(categoryName);
        return found.orElse(null);
    }

    @Transactional
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category addCategory(Category category) {
        if (findCategoryByName(category.getName()) == null) {
            categoryRepository.save(category);
            return category;
        } else
            return null;
    }

    @Transactional
    public List<String> findAllCategoryNames() {
        return categoryRepository.findAllCategoryNames();
    }

    @Transactional
    public void deleteCategory(String name) {
        categoryRepository.deleteByName(name);
    }
}