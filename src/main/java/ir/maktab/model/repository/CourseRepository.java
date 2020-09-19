package ir.maktab.model.repository;

import ir.maktab.model.dto.UserDto;
import ir.maktab.model.entity.Course;
import ir.maktab.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

@org.springframework.stereotype.Repository
public interface CourseRepository extends Repository<Course, Integer>, JpaSpecificationExecutor<Course> {

    Course save(Course course);

    List<Course> findAll();

    @Query("SELECT c FROM Course c ORDER BY c.category.name ASC")
    Page<Course> findAllCoursesWithPagination(Pageable pageable);

    @Query("SELECT COUNT(c) FROM Course c")
    int countAll();

    @Modifying
    @Query("UPDATE Course c SET  c.capacity = :capacity, c.duration = :duration, c.startDate = :startDate, " +
            "c.endDate = :endDate WHERE c.title = :title")
    void updateCourse(@Param("title") String title, @Param("capacity") int capacity, @Param("duration") int duration,
                      @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    void deleteByTitle(String title);

    @Query("SELECT c.title FROM Course c")
    List<String> findAllCourseTitle();

    @Query("SELECT c.title FROM Course c where c.category.name= :categoryName and c.filledCapacity < c.capacity")
    List<String> findNotFilledCoursesTitleByCategory(@Param("categoryName") String categoryName);

    @Query("SELECT c.title FROM Course c where c.category.name= :categoryName")
    List<String> findTitlesByCategoryName(@Param("categoryName") String categoryName);

    Course findByTitle(String courseTitle);

    @Modifying
    @Query("update Course  c set c.filledCapacity = (c.filledCapacity + 1) where c.title = :title")
    void updateFilledCapacityByTitle(@Param("title") String courseTitle);

    Page<Course> findByParticipantsContains(User teacher, Pageable pageable);

    long countByParticipantsContains(User teacher);
}
