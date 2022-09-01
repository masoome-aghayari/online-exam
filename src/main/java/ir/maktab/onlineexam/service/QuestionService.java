package ir.maktab.onlineexam.service;

import ir.maktab.onlineexam.model.dto.ExamDto;
import ir.maktab.onlineexam.model.dto.QuestionDto;
import ir.maktab.onlineexam.model.entity.Exam;
import ir.maktab.onlineexam.model.entity.Question;
import ir.maktab.onlineexam.model.repository.ExamRepository;
import ir.maktab.onlineexam.model.repository.QuestionRepository;
import ir.maktab.onlineexam.service.converter.QuestionDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

   /* @Transactional
    public void deleteExamFromQuestion(String examTitle) {
        List<Question> questions = questionRepository.findByExamsContains(examRepository.findByTitle(examTitle));
        questions.forEach(q -> q.getExams().removeIf(e -> e.getTitle().equals(examTitle)));
        questions.forEach(q -> questionRepository.save(q));
    }*/

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
