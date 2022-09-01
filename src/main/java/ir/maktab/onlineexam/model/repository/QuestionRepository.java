package ir.maktab.onlineexam.model.repository;

import ir.maktab.onlineexam.model.entity.Question;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface QuestionRepository extends Repository<Question, Integer>, JpaSpecificationExecutor<Question> {

    void save(Question q);

    Optional<Question> findByText(String text);
}