package ir.maktab.onlineexam.model.repository;

import ir.maktab.onlineexam.model.entity.Exam;
import ir.maktab.onlineexam.model.entity.Status;
import ir.maktab.onlineexam.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@org.springframework.stereotype.Repository
public interface ExamRepository extends Repository<Exam, Integer>, JpaSpecificationExecutor<Integer> {
    void save(Exam exam);

    List<Exam> findByCreatorOrderByTitle(User user);

    @Query("select exam from Exam exam where exam.course.title= :courseTitle")
    Page<Exam> findAllByCourse(@Param("courseTitle") String courseTitle, Pageable pageable);

    @Query("select count(exam) from Exam exam where exam.course.title= :courseTitle ")
    long countByCourseTitle(@Param("courseTitle") String courseTitle);

    @Query("select count (e) from Exam e where e.course.title = :courseTitle")
    int countExamsByCourseTitle(@Param("courseTitle") String courseTitle);

    void deleteExamByTitle(String title);

    Exam findByTitle(String examTitle);

    @Query("delete from Exam exam  where exam.course.title= :courseTitle")
    void deleteByCourseTitle(@Param("courseTitle") String courseTitle);

    @Query("update Exam exam set exam.status= :newStatus where exam.title= :title")
    void updateStatusByTitle(@Param("title") String title, @Param("newStatus") Status status);

    @Query("select exam from Exam exam where exam.title= :title")
    Exam findQuestionMarksByExamTitle(@Param("title") String title);
}
