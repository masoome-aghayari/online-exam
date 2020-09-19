package ir.maktab.service;

import ir.maktab.model.dto.QuestionDto;
import ir.maktab.model.entity.Question;
import ir.maktab.model.entity.QuestionBank;
import ir.maktab.model.repository.QuestionBankRepository;
import ir.maktab.service.converter.QuestionDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionBankService {
    @Autowired
    QuestionBankRepository questionBankRepository;
    @Autowired
    QuestionDtoConverter questionDtoConverter;

    @Transactional
    public void save(QuestionBank questionBank) {
        questionBankRepository.save(questionBank);
    }

    @Transactional
    public List<QuestionDto> findQuestionsByCategoryName(String categoryName) {
        List<Question> questions = questionBankRepository.findAllByCategoryName(categoryName);
        return questions.isEmpty() ? null : questionDtoConverter.convertQuestionListToDto(questions);
    }
}
