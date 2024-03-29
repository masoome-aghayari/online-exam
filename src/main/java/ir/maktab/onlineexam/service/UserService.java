package ir.maktab.onlineexam.service;

import ir.maktab.onlineexam.model.dto.UserDto;
import ir.maktab.onlineexam.model.entity.*;
import ir.maktab.onlineexam.model.repository.UserRepository;
import ir.maktab.onlineexam.model.repository.UserSpecifications;
import ir.maktab.onlineexam.service.converter.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@PropertySources({@PropertySource("classpath:message.properties"),
        @PropertySource("classpath:constant-numbers.properties")})
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDtoConverter userDtoConverter;
    private final RoleService roleService;
    private final CourseService courseService;
    private final Environment env;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       UserDtoConverter userDtoConverter, RoleService roleService,
                       CourseService courseService, Environment env) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDtoConverter = userDtoConverter;
        this.roleService = roleService;
        this.courseService = courseService;
        this.env = env;
    }

    @Transactional
    public UserDto registerNewUser(UserDto userDto) {
        User user = userDtoConverter.convertToSaveUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepository.save(user);
        return userDtoConverter.convertUserToDto(savedUser);
    }

    @Transactional
    public UserDto findByEmail(String email) {
        Optional<User> desiredUser = userRepository.findByEmail(email);
        return desiredUser.map(user -> userDtoConverter.convertUserToDto(user)).orElse(null);
    }

    @Transactional
    public User findUserByEmail(String email) {
        Optional<User> byEmail = userRepository.findByEmail(email);
        return byEmail.orElse(null);
    }

    @Transactional
    public Status findUserStatus(String email) {
        return userRepository.findUserStatus(email);
    }

    @Transactional
    public int findNumberOfPendingUsers(Status status) {
        int totalPending = userRepository.findByStatus(status, RoleName.ADMIN.name()).size();
        int rowsNumberInPage = Integer.parseInt(env.getProperty("rows.per.page"));
        double pages = (double) totalPending / rowsNumberInPage;
        return (int) Math.ceil(pages);
    }

    @Transactional
    public Page<UserDto> findAllPending(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.Direction.ASC, "family");
        Page<User> page = userRepository.findAllByStatus(Status.PENDING, pageable);
        return userDtoConverter.convertUserPageToDtoPage(page);
    }

    @Transactional
    public List<UserDto> findAll() {
        List<User> allUsers = userRepository.findAll();
        return userDtoConverter.convertUserListToDtoList(allUsers);
    }

    @Transactional
    public void updateUserStatus(String email, Status status) {
        Optional<User> found = userRepository.findByEmail(email.toLowerCase());
        if (found.isPresent()) {
            userRepository.updateByStatus(email, status);
        }
    }

    @Transactional
    public List<UserDto> findMaxMatch(UserDto userDto, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.Direction.ASC, "family");
        Role role = roleService.getRoleByName(userDto.getRole());
        Page<User> matchedUsers = userRepository.findAll(UserSpecifications.findMaxMatch(userDto.getName(),
                userDto.getFamily(), userDto.getEmail(), role), pageable);
        return userDtoConverter.convertUserPageToDtoPage(matchedUsers).getContent();
    }

    @Transactional
    public int getTotalNumberOfPages(UserDto userDto) {
        Role role = roleService.getRoleByName(userDto.getRole());
        long totalMatched = userRepository.count(UserSpecifications.findMaxMatch(userDto.getName(), userDto.getFamily(),
                userDto.getEmail(), role));
        int rowsNumberInPage = Integer.parseInt(env.getProperty("rows.per.page"));
        double pages = (double) totalMatched / rowsNumberInPage;
        return (int) Math.ceil(pages);
    }

    @Transactional
    public void updateUser(UserDto userDto) {
        Role role = userDtoConverter.stringToRoleConverter(userDto.getRole());
        userRepository.update(userDto.getName(), userDto.getFamily(), role, userDto.getEmail());
    }

    @Transactional
    public void updateWhenConfirm(UserDto userDto) {
        Role role = userDtoConverter.stringToRoleConverter(userDto.getRole());
        userRepository.updateWhenConfirm(userDto.getEmail(), userDto.getName(), userDto.getFamily(), role, Status.ACTIVE);
    }

    @Transactional
    public boolean isExists() {
        return userRepository.countAll() != 0;
    }

    @Transactional
    public boolean isExistsUserWithRole(String role) {
        return userRepository.countUserByRole(roleService.getRoleByName(role)) != 0;
    }

    @Transactional
    public List<UserDto> findUserByRoleAndCourseTitle(String roleName, String courseTitle, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.Direction.ASC, "family");
        Role role = roleService.getRoleByName(roleName);
        Course course = courseService.findCourseByTitle(courseTitle);
        Page<User> matchedUsers = userRepository.findUsersByRoleAndCoursesNotContains(role, course, pageable);
        return userDtoConverter.convertUserPageToDtoPage(matchedUsers).getContent();
    }

    @Transactional
    public void updateUserCourses(UserDto userDto, String courseTitle) {
        Course course = courseService.findCourseByTitle(courseTitle);
        course.setFilledCapacity(course.getFilledCapacity() + 1);
        Optional<User> found = userRepository.findByEmail(userDto.getEmail());
        if (found.isPresent()) {
            User desiredUser = found.get();
            Set<Course> userCourses = Objects.isNull(desiredUser.getCourses()) ? new HashSet<>() : desiredUser.getCourses();
            userCourses.add(course);
            desiredUser.setCourses(userCourses);
            course.getParticipants().add(desiredUser);
          //  courseService.addCourse(course);
            userRepository.save(desiredUser);
        }
    }

    @Transactional
    public int countTotalPagesOfMatchedUsers(String roleName, String courseTitle) {
        Role role = roleService.getRoleByName(roleName);
        Course course = courseService.findCourseByTitle(courseTitle);
        long totalMatched = userRepository.countUserByRoleAndCoursesNotContains(role, course);
        int rowsNumberInPage = Integer.parseInt(env.getProperty("rows.per.page"));
        double pages = (double) totalMatched / rowsNumberInPage;
        return (int) Math.ceil(pages);
    }

    @Transactional
    public void deleteCourseFromUserByTitle(String courseTitle) {
        List<User> users = userRepository.findByCoursesContains(courseService.findCourseByTitle(courseTitle));
        users.forEach(user -> user.getCourses().removeIf(course -> course.getTitle().equals(courseTitle)));
        users.forEach(user -> userRepository.save(user));
    }

    public void confirmAllPendingUsers() {
        userRepository.confirmAllPendingUsers();
    }
}