package ir.maktab.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String text;
    @Enumerated(EnumType.STRING)
    private QuestionType type;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> options;
    private int rightAnswerIndex;
    private String userAnswer;
}