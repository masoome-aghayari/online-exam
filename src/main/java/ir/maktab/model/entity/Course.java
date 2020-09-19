package ir.maktab.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
    private int duration;
    private int capacity;
    private int filledCapacity;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> participants;
    @OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
    private Set<Exam> exams;

    public void setEndDate() {
        this.endDate = this.startDate.plusMonths(this.duration);
    }
}