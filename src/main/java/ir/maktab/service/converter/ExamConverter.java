package ir.maktab.service.converter;

import ir.maktab.model.dto.ExamDto;
import ir.maktab.model.entity.Course;
import ir.maktab.model.entity.Exam;
import ir.maktab.model.entity.Status;
import ir.maktab.service.CourseService;
import ir.maktab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamConverter {

    @Autowired
    CourseDtoConverter courseDtoConverter;
    @Autowired
    UserDtoConverter userDtoConverter;
    @Autowired
    CourseService courseService;
    @Autowired
    UserService userService;

    public Exam convertDtoToSaveExam(ExamDto examDto, String courseTitle) {
        Course course = courseService.findCourseByTitle(courseTitle);
        Exam exam = new Exam();
        exam.setCourse(course);
        exam.setCreator(userService.findUserByEmail(examDto.getCreatorDto().getEmail()));
        exam.setTitle(examDto.getTitle());
        exam.setDescription(examDto.getDescription());
        exam.setDuration(examDto.getDuration());
        exam.setStartDate(courseDtoConverter.stringToLocalDate(examDto.getStartDate()));
        exam.setEndDate(courseDtoConverter.stringToLocalDate(examDto.getEndDate()));
        exam.setStatus(Status.PENDING);
        return exam;
        //TODO: exam.setQuestions();
    }

    public ExamDto convertToExamDto(Exam exam) {
        ExamDto examDto = new ExamDto();
        examDto.setTitle(exam.getTitle());
        examDto.setCourseDto(courseDtoConverter.convertCourseToDto(exam.getCourse()));
        examDto.setCreatorDto(userDtoConverter.convertUserToDto(exam.getCreator()));
        examDto.setDescription(exam.getDescription());
        examDto.setDuration(exam.getDuration());
        examDto.setStatus(exam.getStatus().name());
        examDto.setStartDate(exam.getStartDate().toString());
        examDto.setEndDate(exam.getEndDate().toString());
        return examDto;
        //TODO: examDto.setQuestionsDto();
    }

    public List<ExamDto> convertPageOfExamsToDtoList(Page<Exam> exams) {
        return convertListOfExamToDto(exams.getContent());
    }

    public List<ExamDto> convertListOfExamToDto(List<Exam> exams) {
        return exams.stream().map(this::convertToExamDto).collect(Collectors.toList());
    }
}
