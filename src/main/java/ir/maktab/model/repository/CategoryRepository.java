package ir.maktab.model.repository;

import ir.maktab.model.entity.Category;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface CategoryRepository extends Repository<Category, Integer>, JpaSpecificationExecutor<Category> {
    List<Category> findAll();

    Optional<Category> findByName(String categoryName);

    void save(Category category);

    @Query("SELECT c.name FROM Category c")
    List<String> findAllCategoryNames();

    void deleteByName(String name);
}
