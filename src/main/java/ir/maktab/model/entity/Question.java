package ir.maktab.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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
    @ElementCollection
    private List<String> options;
    private int rightAnswerIndex;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Exam> exams;
    @Transient
    private String userAnswer;
}