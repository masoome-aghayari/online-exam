package ir.maktab.service.converter;

import ir.maktab.model.dto.QuestionDto;
import ir.maktab.model.entity.Question;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionDtoConverter {
    public QuestionDto convertQuestionToDto(Question question) {
        return null;  //TODO
    }

    public Question convertQuestionDtoToSave(QuestionDto questionDto) {
        return null; //TODO
    }

    public List<QuestionDto> convertQuestionListToDto(List<Question> questions) {
        return questions.stream().map(this::convertQuestionToDto).collect(Collectors.toList());
    }

    public Set<Question> convertDtoSetToQuestionSet(Set<QuestionDto> questionDtos) {
        return null;//TODO
    }
}
