package ir.maktab.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ir.maktab.model.entity.Course;
import ir.maktab.model.entity.Status;
import ir.maktab.util.CustomExamDtoDeserializer;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class UserDto {
    private String name;
    private String family;
    private String password;
    private String confirmPassword;
    private String email;
    private String role;
    private Status status;
    private Set<Course> courses;
    @JsonDeserialize(keyUsing = CustomExamDtoDeserializer.class)
    private Map<ExamDto, Integer> examDtoMark;
}
