package ir.maktab.service.converter;

import ir.maktab.model.dto.QuestionDto;
import ir.maktab.model.entity.Exam;
import ir.maktab.model.entity.Question;
import ir.maktab.model.entity.QuestionType;
import ir.maktab.model.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionDtoConverter {
    @Autowired
    ExamRepository examRepository;

    public QuestionDto convertQuestionToDto(Question question) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setType(question.getType().name());
        questionDto.setText(question.getText());
        questionDto.setOptions(question.getOptions());
        questionDto.setRightAnswerIndex(question.getRightAnswerIndex());
        questionDto.setTitle(question.getTitle());
        return questionDto;
    }

    public Question convertQuestionDtoToSave(QuestionDto questionDto) {
        Question question = new Question();
        Exam exam = examRepository.findByTitle(questionDto.getExams().get(0).getTitle());
        question.setTitle(questionDto.getTitle());
        question.setText(questionDto.getText());
        question.setType(QuestionType.valueOf(questionDto.getType()));
        if (questionDto.getType().equals(QuestionType.TEST.name())) {
            question.setOptions(questionDto.getOptions());
            question.setRightAnswerIndex(questionDto.getRightAnswerIndex());
        }
        return question;
    }

    public List<QuestionDto> convertQuestionListToDto(List<Question> questions) {
        return questions.stream().map(this::convertQuestionToDto).collect(Collectors.toList());
    }

    public Set<Question> convertDtoSetToQuestionSet(Set<QuestionDto> questionDtos) {
        return null;//TODO
    }
}