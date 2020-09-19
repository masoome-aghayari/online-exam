package ir.maktab.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Map;

@Entity
@Getter
@Setter
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    @ManyToOne
    private Course course;
    @ManyToOne
    private User creator;
    private int duration;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ElementCollection
    @MapKeyJoinColumn(name = "question_id")
    @Column(name = "question_mark")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<Question, Integer> questionMarks;
}