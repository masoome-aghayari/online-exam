package ir.maktab.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ir.maktab.util.CustomQuestionKeyDeserializer;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class ExamDto {
    private String title;
    private String description;
    private CourseDto courseDto;
    private UserDto creatorDto;
    private int duration;
    private String startDate;
    private String endDate;
    private String status;
    @JsonDeserialize(keyUsing = CustomQuestionKeyDeserializer.class)
    private Map<QuestionDto, Integer> questionDtoMarks;
}
