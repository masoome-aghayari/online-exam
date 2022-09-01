package ir.maktab.onlineexam.service.converter;

import ir.maktab.onlineexam.model.dto.CourseDto;
import ir.maktab.onlineexam.model.entity.Category;
import ir.maktab.onlineexam.model.entity.Course;
import ir.maktab.onlineexam.service.CategoryService;
import ir.maktab.onlineexam.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CourseDtoConverter {

    @Autowired
    CategoryService categoryService;
    @Autowired
    CourseService courseService;

    public Category stringToCategoryConverter(String categoryName) {
        return categoryService.findCategoryByName(categoryName);
    }

    public Course convertToSaveCourse(CourseDto courseDto) {
        Course course = new Course();
        int numberOfCourses = courseService.getNumberOfCourses();
        course.setTitle(courseDto.getCategory() + (numberOfCourses + 1));
        course.setCategory(stringToCategoryConverter(courseDto.getCategory()));
        course.setDuration(stringToIntConverter(courseDto.getDuration()));
        course.setCapacity(stringToIntConverter(courseDto.getCapacity()));
        course.setFilledCapacity(0);
        course.setStartDate(stringToLocalDate(courseDto.getStartDate()));
        course.setEndDate();
        return course;
    }

    public LocalDate stringToLocalDate(String dateString) {
        if (Objects.isNull(dateString) || dateString.length() == 0)
            return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    public CourseDto convertCourseToDto(Course course) {
        CourseDto courseDto = new CourseDto();
        courseDto.setCategory(course.getCategory().getName());
        courseDto.setTitle(course.getTitle());
        courseDto.setDuration(String.valueOf(course.getDuration()));
        courseDto.setCapacity(String.valueOf(course.getCapacity()));
        courseDto.setFilledCapacity(String.valueOf(course.getFilledCapacity()));
        courseDto.setStartDate(course.getStartDate().toString());
        courseDto.setEndDate(course.getEndDate().toString());
        return courseDto;
    }


    public List<CourseDto> convertCourseListToDtoList(List<Course> courses) {
        return courses.stream().map(this::convertCourseToDto).collect(Collectors.toList());
    }

    public Course convertDtoToCourseToUpdate(CourseDto courseDto) {
        Course course = new Course();
        course.setTitle(courseDto.getTitle());
        course.setCategory(stringToCategoryConverter(courseDto.getCategory()));
        course.setDuration(stringToIntConverter(courseDto.getDuration()));
        course.setCapacity(stringToIntConverter(courseDto.getCapacity()));
        course.setFilledCapacity(stringToIntConverter(courseDto.getFilledCapacity()));
        course.setStartDate(stringToLocalDate(courseDto.getStartDate()));
        course.setEndDate();
        return course;
    }

    public int stringToIntConverter(String intString) {
        if (Objects.isNull(intString) || intString.length() == 0)
            return 0;
        else
            return Integer.parseInt(intString);
    }

    public List<CourseDto> convertCoursePageToDtoList(Page<Course> teacherCourses) {
        return convertCourseListToDtoList(teacherCourses.getContent());
    }
}