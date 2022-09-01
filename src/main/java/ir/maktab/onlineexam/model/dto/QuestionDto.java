package ir.maktab.onlineexam.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class QuestionDto {
    private String title;
    private String type;
    private String text;
    private List<String> options;
    private int rightAnswerIndex;
    private String userAnswer;
    private List<ExamDto> exams;
    private boolean addToBank;
}
