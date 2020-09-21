package ir.maktab.service;

import ir.maktab.model.dto.ExamDto;
import ir.maktab.model.dto.QuestionDto;
import ir.maktab.model.entity.Exam;
import ir.maktab.model.entity.Question;
import ir.maktab.model.repository.ExamRepository;
import ir.maktab.model.repository.QuestionRepository;
import ir.maktab.service.converter.QuestionDtoConverter;
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
    @Autowired
    QuestionDtoConverter questionDtoConverter;
    @Autowired
    ExamService examService;
    @Autowired
    QuestionBankService questionBankService;

    @Transactional
    public void deleteExamFromQuestion(String examTitle) {
        List<Question> questions = questionRepository.findByExamsContains(examRepository.findByTitle(examTitle));
        questions.forEach(q -> q.getExams().removeIf(e -> e.getTitle().equals(examTitle)));
        questions.forEach(q -> questionRepository.save(q));
    }

    public boolean addQuestion(QuestionDto questionDto, ExamDto examDto, int mark) {
        if (isExists(questionDto.getText()))
            return true;
        else {
            Question question = questionDtoConverter.convertQuestionDtoToSave(questionDto);
            questionRepository.save(question);
            Exam exam = examRepository.findByTitle(examDto.getTitle());
            exam.getQuestionMarks().put(question, mark);
            examRepository.save(exam);
            if (questionDto.isAddToBank())
                questionBankService.addQuestionToBank(question, exam);
            return false;
        }
    }

    public boolean isExists(String text) {
        return questionRepository.findByText(text).isPresent();
    }
}
