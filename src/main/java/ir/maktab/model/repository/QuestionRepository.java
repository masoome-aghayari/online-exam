package ir.maktab.model.repository;

import ir.maktab.model.entity.Exam;
import ir.maktab.model.entity.Question;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface QuestionRepository extends Repository<Question, Integer>, JpaSpecificationExecutor<Question> {

    List<Question> findByExamsContains(Exam exam);

    void save(Question q);

    Optional<Question> findByText(String text);
}