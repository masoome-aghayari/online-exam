package ir.maktab.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class CourseDto {
    private String title;
    private String category;
    private String duration;
    private String capacity;
    private String filledCapacity;
    private String startDate;
    private String endDate;
    private Set<UserDto> userDtos;
    private Set<ExamDto> exams;
}
