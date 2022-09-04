package ir.maktab.onlineexam.model.repository;

import ir.maktab.onlineexam.model.entity.Course;
import ir.maktab.onlineexam.model.entity.Role;
import ir.maktab.onlineexam.model.entity.Status;
import ir.maktab.onlineexam.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@org.springframework.stereotype.Repository
public interface UserRepository<findByUserStatusW> extends Repository<User, Integer>, JpaSpecificationExecutor<User> {

    User save(User user);

    @Modifying
    @Query("update User set name= :newName, family= :newFamily, role= :newRole where email= :email")
    void update(@Param("newName") String newName, @Param("newFamily") String newFamily, @Param("newRole") Role role,
                @Param("email") String email);

    @Modifying
    @Query("update User user set user.status = :status where user.email = :email")
    void updateByStatus(@Param("email") String email, @Param("status") Status status);

    @Modifying
    @Query("update User u set u.status= 'ACTIVE' where u.status= 'PENDING'")
    void confirmAllPendingUsers();

    List<User> findAll();

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

    @Query("select u from User u where u.status= :status and not u.role.roleName= :role")
    List<User> findByStatus(@Param("status") Status status, @Param("role") String role);

    Page<User> findAllByStatus(Status pending, Pageable pageable);

    @Query("select u.status from User u where u.email= :email")
    Status findUserStatus(@Param("email") String email);

    @Query("SELECT COUNT(u) FROM User u")
    int countAll();

    int countUserByRole(Role role);

    Page<User> findUsersByRoleAndCoursesNotContains(Role role, Course course, Pageable pageable);

    long countUserByRoleAndCoursesNotContains(Role role, Course course);

    @Modifying
    @Query("update User u set u.courses = :courses where u.email = :email")
    void updateCoursesByEmail(@Param("email") String email, @Param("courses") Set<Course> userCourses);

    List<User> findByCoursesContains(Course course);

    @Modifying
    @Query("update User set name= :newName, family= :newFamily, role= :newRole, status=:newStatus where email= :email")
    void updateWhenConfirm(@Param("email") String email, @Param("newName") String name, @Param("newFamily") String family,
                           @Param("newRole") Role role, @Param("newStatus") Status status);
}