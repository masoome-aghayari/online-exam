package ir.maktab.service;

import ir.maktab.model.entity.Exam;
import ir.maktab.model.entity.Question;
import ir.maktab.model.repository.ExamRepository;
import ir.maktab.model.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    ExamRepository examRepository;

    @Transactional
    public void deleteExamFromQuestion(String examTitle){
        List<Question> questions = questionRepository.findByExamsContains(examRepository.findByTitle(examTitle));
        questions.forEach(q -> q.getExams().removeIf(e -> e.getTitle().equals(examTitle)));
        questions.forEach(q -> questionRepository.save(q));
    }
}
