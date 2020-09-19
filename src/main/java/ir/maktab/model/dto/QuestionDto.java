package ir.maktab.model.dto;

import java.util.Set;

public class QuestionDto {
    private String title;
    private String type;
    private String text;
    private Set<OptionDto> options;
    private String rightAnswer;
    private String userAnswer;
    private Set<ExamDto> exams;
}
