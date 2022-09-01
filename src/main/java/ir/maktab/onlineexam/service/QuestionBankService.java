package ir.maktab.onlineexam.service;

import ir.maktab.onlineexam.model.dto.ExamDto;
import ir.maktab.onlineexam.model.dto.QuestionDto;
import ir.maktab.onlineexam.model.entity.Exam;
import ir.maktab.onlineexam.model.entity.Question;
import ir.maktab.onlineexam.model.entity.QuestionBank;
import ir.maktab.onlineexam.model.repository.ExamRepository;
import ir.maktab.onlineexam.model.repository.QuestionBankRepository;
import ir.maktab.onlineexam.service.converter.QuestionDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class QuestionBankService {
    @Autowired
    QuestionBankRepository questionBankRepository;
    @Autowired
    QuestionDtoConverter questionDtoConverter;
    @Autowired
    ExamRepository examRepository;

    @Transactional
    public void save(QuestionBank questionBank) {
        questionBankRepository.save(questionBank);
    }

    @Transactional
    public List<QuestionDto> findQuestionsByCategoryName(ExamDto examDto) {
        List<Question> bankQuestions = questionBankRepository.findAllByCategoryName(examDto.getCourseDto().getCategory());
        if (bankQuestions.isEmpty())
            return null;
        Map<Question, Integer> questionMarks = examRepository.findQuestionMarksByExamTitle(examDto.getTitle()).getQuestionMarks();
        Set<Question> examQuestionsSet = questionMarks.keySet();
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question : bankQuestions) {
            if (!examQuestionsSet.contains(question))
                questionDtos.add(questionDtoConverter.convertQuestionToDto(question));
        }
        return questionDtos;
    }

    public void addQuestionToBank(Question question, Exam exam) {
        Optional<QuestionBank> found = questionBankRepository.findByCategory(exam.getCourse().getCategory());
        QuestionBank questionBank;
        questionBank = found.orElseGet(QuestionBank::new);
        if (questionBank.getQuestion() == null) {
            questionBank.setQuestion(new HashSet<>());
            questionBank.setCategory(exam.getCourse().getCategory());
        }
        questionBank.getQuestion().add(question);
        questionBankRepository.save(questionBank);
    }
}
