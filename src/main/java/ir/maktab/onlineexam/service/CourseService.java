package ir.maktab.onlineexam.service;

import ir.maktab.onlineexam.model.dto.CourseDto;
import ir.maktab.onlineexam.model.dto.UserDto;
import ir.maktab.onlineexam.model.entity.Category;
import ir.maktab.onlineexam.model.entity.Course;
import ir.maktab.onlineexam.model.entity.User;
import ir.maktab.onlineexam.model.repository.CourseRepository;
import ir.maktab.onlineexam.model.repository.CourseSpecifications;
import ir.maktab.onlineexam.service.converter.CourseDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@PropertySources({@PropertySource("classpath:message.properties"),
        @PropertySource("classpath:constant-numbers.properties")})
public class CourseService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseDtoConverter courseDtoConverter;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    ExamService examService;
    @Autowired
    Environment env;

    @Transactional
    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    @Transactional
    public void deleteCourse(String courseTitle) {
        userService.deleteCourseFromUserByTitle(courseTitle);
        examService.deleteByCourseTitle(courseTitle);
        courseRepository.deleteByTitle(courseTitle);
    }

    @Transactional
    public List<CourseDto> findMaxMatch(CourseDto courseDto, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.Direction.ASC, "category");
        Category category = categoryService.findCategoryByName(courseDto.getCategory());
        LocalDate startDate = courseDtoConverter.stringToLocalDate(courseDto.getStartDate());
        LocalDate endDate = courseDtoConverter.stringToLocalDate(courseDto.getEndDate());
        int duration = courseDtoConverter.stringToIntConverter(courseDto.getDuration());
        int capacity = courseDtoConverter.stringToIntConverter(courseDto.getCapacity());
        Page<Course> matchedCourses = courseRepository.findAll(
                CourseSpecifications.findMaxMatch(category, courseDto.getTitle(), duration, capacity,
                        startDate, endDate), pageable);
        return courseDtoConverter.convertCourseListToDtoList(matchedCourses.getContent());
    }

    @Transactional
    public int getNumberOfPages() {
        int totalCourses = courseRepository.countAll();
        int rowsNumberInPage = Integer.parseInt(env.getProperty("rows.per.page"));
        double pages = (double) totalCourses / rowsNumberInPage;
        return (int) Math.ceil(pages);
    }

    @Transactional
    public int getNumberOfCourses() {
        return courseRepository.countAll();
    }

    @Transactional
    public List<CourseDto> findAll(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.Direction.ASC, "title");
        Page<Course> courses = courseRepository.findAllCoursesWithPagination(pageable);
        return courseDtoConverter.convertCourseListToDtoList(courses.getContent());
    }

    @Transactional
    public void updateCourse(CourseDto courseDto) {
        Course course = courseDtoConverter.convertDtoToCourseToUpdate(courseDto);
        courseRepository.updateCourse(course.getTitle(), course.getCapacity(), course.getDuration(),
                course.getStartDate(), course.getEndDate());
    }

    @Transactional
    public long getNumberOfMatchedPages(CourseDto courseDto) {
        Category category = categoryService.findCategoryByName(courseDto.getCategory());
        LocalDate startDate = courseDtoConverter.stringToLocalDate(courseDto.getStartDate());
        LocalDate endDate = courseDtoConverter.stringToLocalDate(courseDto.getEndDate());
        int capacity = courseDtoConverter.stringToIntConverter(courseDto.getCapacity());
        int duration = courseDtoConverter.stringToIntConverter(courseDto.getDuration());
        long totalMatched = courseRepository.count(CourseSpecifications.findMaxMatch(category, courseDto.getTitle(),
                duration, capacity, startDate, endDate));
        int rowsNumberInPage = Integer.parseInt(env.getProperty("rows.per.page"));
        double pages = (double) totalMatched / rowsNumberInPage;
        return (int) Math.ceil(pages);
    }

    @Transactional
    public List<String> findAllCourseTitles() {
        return courseRepository.findAllCourseTitle();
    }

    @Transactional
    public List<String> findProperNotFilledCourses(String categoryName) {
        return courseRepository.findNotFilledCoursesTitleByCategory(categoryName);
    }

    @Transactional
    public List<String> findCourseTitlesByCategoryName(String categoryName) {
        return courseRepository.findTitlesByCategoryName(categoryName);
    }

    @Transactional
    public Course findCourseByTitle(String courseTitle) {
        return courseRepository.findByTitle(courseTitle);
    }

    @Transactional
    public void updateFilledCapacity(String courseTitle) {
        courseRepository.updateFilledCapacityByTitle(courseTitle);
    }

    @Transactional
    public List<CourseDto> findTeacherCourses(UserDto teacherDto, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.Direction.ASC, "id");
        User teacher = userService.findUserByEmail(teacherDto.getEmail());
        Page<Course> teacherCoursesPage = courseRepository.findByParticipantsContains(teacher, pageable);
        return courseDtoConverter.convertCoursePageToDtoList(teacherCoursesPage);
    }

    @Transactional
    public long getNumberOfTeacherCoursesPage(UserDto teacherDto) {
        User teacher = userService.findUserByEmail(teacherDto.getEmail());
        long totalCourses = courseRepository.countByParticipantsContains(teacher);
        int rowsNumberInPage = Integer.parseInt(env.getProperty("rows.per.page"));
        double pages = (double) totalCourses / rowsNumberInPage;
        return (int) Math.ceil(pages);
    }
}