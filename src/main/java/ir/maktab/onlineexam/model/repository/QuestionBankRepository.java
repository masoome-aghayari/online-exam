package ir.maktab.onlineexam.model.repository;

import ir.maktab.onlineexam.model.entity.Category;
import ir.maktab.onlineexam.model.entity.Question;
import ir.maktab.onlineexam.model.entity.QuestionBank;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface QuestionBankRepository extends Repository<QuestionBank, Integer>, JpaSpecificationExecutor<QuestionBank> {
    @Query("select qb.question from QuestionBank qb where qb.category.name = :categoryName")
    List<Question> findAllByCategoryName(@Param("categoryName") String categoryName);

    void save(QuestionBank questionBank);

    Optional<QuestionBank> findByCategory(Category category);
}
