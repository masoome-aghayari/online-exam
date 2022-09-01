package ir.maktab.onlineexam.model.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

@Entity
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String family;
    private String password;
    private String email;
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ElementCollection
    @MapKeyJoinColumn(name = "exam_id")
    @Column(name = "exam_mark")
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<Exam, Double> examMarks;
    @ManyToMany(mappedBy = "participants", fetch = FetchType.EAGER)
    private Set<Course> courses;
}