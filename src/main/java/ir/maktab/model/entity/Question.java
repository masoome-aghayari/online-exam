package ir.maktab.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Option> options;
    private String rightAnswer;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Exam> exams;
    @Transient
    private String userAnswer;

}