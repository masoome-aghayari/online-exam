package ir.maktab.onlineexam.controller;

import ir.maktab.onlineexam.model.dto.CourseDto;
import ir.maktab.onlineexam.model.dto.ResponseModel;
import ir.maktab.onlineexam.model.dto.UserDto;
import ir.maktab.onlineexam.model.entity.Category;
import ir.maktab.onlineexam.model.entity.RoleName;
import ir.maktab.onlineexam.service.CategoryService;
import ir.maktab.onlineexam.service.CourseService;
import ir.maktab.onlineexam.service.RoleService;
import ir.maktab.onlineexam.service.UserService;
import ir.maktab.onlineexam.service.converter.CourseDtoConverter;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/course")
@PropertySource("classpath:message.properties")
@PreAuthorize("hasAuthority('ADMIN')")
public class CourseController {
    private final CourseService courseService;
    private final CourseDtoConverter courseDtoConverter;
    private final CategoryService categoryService;
    private final UserService userService;
    private final RoleService roleService;
    private final Environment env;

    public CourseController(CourseService courseService, CourseDtoConverter courseDtoConverter,
                            CategoryService categoryService, UserService userService,
                            RoleService roleService, Environment env) {
        this.courseService = courseService;
        this.courseDtoConverter = courseDtoConverter;
        this.categoryService = categoryService;
        this.userService = userService;
        this.roleService = roleService;
        this.env = env;
    }

    @GetMapping(value = "")
    public String courseMenu() {
        return "courseMenu";
    }

    @GetMapping(value = "/add")
    public ModelAndView showAddForm(Model model) {
        ModelAndView add = new ModelAndView("addCourse");
        List<Category> categories = categoryService.findAll();
        if (categories.isEmpty())
            return new ModelAndView("message", "message",
                    env.getProperty("No.Category.Found"));
        add.addObject("categories", categories);
        add.addObject("courseDto", new CourseDto());
        add.addObject("message", model.getAttribute("message"));
        return add;
    }

    @PostMapping(value = "/add")
    public ResponseModel<String> addCourse(CourseDto courseDto, Model model) {
        courseService.addCourse(courseDto);
        return new ResponseModel<>(env.getProperty("course.added.successfully"));
    }

    @GetMapping(value = "/delete")
    public ModelAndView showDeleteForm(Model model) {
        List<String> titleList = courseService.findAllCourseTitles();
        if (titleList.isEmpty())
            return new ModelAndView("message", "message", env.getProperty("No.Course.Found"));
        ModelAndView delete = new ModelAndView("deleteCourse");
        delete.addObject("titleList", titleList)
                .addObject("message", model.getAttribute("message"));
        return delete;
    }

    @PostMapping(value = "/delete")
    public ResponseModel<String> deleteCourse(@RequestParam(required = false, name = "title") String title, Model model) {
        courseService.deleteCourse(title);
        return new ResponseModel<>(env.getProperty("course.deleted.successfully"));
    }

    @GetMapping(value = "searchProcess/{pageNumber}")
    public ModelAndView searchProcess(@ModelAttribute("courseDto") CourseDto courseDto, Model model,
                                      @PathVariable(required = false) int pageNumber) {
        long totalPages = courseService.getNumberOfMatchedPages(courseDto);
        if (totalPages == 0)
            return new ModelAndView("message", "message", env.getProperty("No.Course.Found"));
        int limit = Integer.parseInt(env.getProperty("rows.per.page"));
        List<CourseDto> matchedCourses = courseService.findMaxMatch(courseDto, pageNumber - 1, limit);
        ModelAndView searchResult = new ModelAndView("searchCourse");
        searchResult.addObject("courses", matchedCourses)
                .addObject("categories", categoryService.findAll())
                .addObject("pageNumber", pageNumber)
                .addObject("totalPages", totalPages)
                .addObject("courseDto", courseDto);
        return searchResult;
    }

    @PostMapping(value = "/saveChanges", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String editCourse(@RequestBody CourseDto courseDto) {
        courseService.updateCourse(courseDto);
        return env.getProperty("update.successful");
    }

    @GetMapping(value = "/addParticipant")
    public ModelAndView showAddParticipantForm(Model model) {
        if (!userService.isExists())
            return new ModelAndView("message", "message", env.getProperty("no.user.found"));
        ModelAndView addParticipants = new ModelAndView("addParticipants");
        List<String> categories = categoryService.findAllCategoryNames();
        List<String> roles = roleService.getUserRoles();
        UserDto userDto = (UserDto) model.getAttribute("userDto");
        addParticipants.addObject("categories", categories)
                .addObject("categoryName", model.getAttribute("categoryName"))
                .addObject("courseTitles", model.getAttribute("courseTitles"))
                .addObject("courseTitle", model.getAttribute("courseTitle"))
                .addObject("userDtos", model.getAttribute("userDtos"))
                .addObject("userDto", userDto)
                .addObject("roles", roles)
                .addObject("roleName", model.getAttribute("roleName"))
                .addObject("message", model.getAttribute("message"))
                .addObject("totalPages", model.getAttribute("totalPages") == null ? 1 : model.getAttribute("totalPages"))
                .addObject("pageNumber", model.getAttribute("pageNumber") == null ? 1 : model.getAttribute("pageNumber"));
        return addParticipants;
    }

    @GetMapping(value = "/addParticipant/checkRole/{roleName}")
    public ModelAndView isExistsUser(@PathVariable(required = false, name = "roleName") String roleName, Model model) {
        if (!userService.isExistsUserWithRole(roleName))
            model.addAttribute("message", env.getProperty("No.User.With.Role.Found"));
        model.addAttribute("roleName", roleName);
        return showAddParticipantForm(model);
    }

    @GetMapping(value = "/addParticipant/find-courses/{roleName}/{categoryName}")
    public ModelAndView findProperCourseTitles(@PathVariable(name = "roleName") String roleName, Model model,
                                               @PathVariable(name = "categoryName") String categoryName) {
        List<String> courseTitleList;
        if (roleName.equals(RoleName.STUDENT.name()))
            courseTitleList = courseService.findProperNotFilledCourses(categoryName);
        else
            courseTitleList = courseService.findCourseTitlesByCategoryName(categoryName);
        if (courseTitleList.isEmpty())
            model.addAttribute("message", env.getProperty("No.Course.With.Category.Found"));
        else
            model.addAttribute("courseTitles", courseTitleList);
        model.addAttribute("roleName", roleName)
                .addAttribute("categoryName", categoryName);
        return showAddParticipantForm(model);
    }

    @GetMapping(value = "/addParticipant/find-userList/{roleName}/{categoryName}/{courseTitle}/{pageNumber}")
    public ModelAndView findProperUsers(@PathVariable(name = "roleName") String roleName,
                                        @PathVariable(name = "categoryName") String categoryName,
                                        @PathVariable(name = "courseTitle") String courseTitle,
                                        @PathVariable(name = "pageNumber") int pageNumber, Model model) {
        if (pageNumber < 1)
            pageNumber = 1;
        int totalPages = userService.countTotalPagesOfMatchedUsers(roleName, courseTitle);
        if (pageNumber > totalPages)
            pageNumber = totalPages;
        if (totalPages == 0) {
            model.addAttribute("message", env.getProperty("no.user.found"));
        } else {
            int limit = Integer.parseInt(env.getProperty("rows.per.page"));
            List<UserDto> properUsers = userService.findUserByRoleAndCourseTitle(roleName, courseTitle, (pageNumber - 1), limit);
            if (pageNumber > 1 && properUsers.isEmpty())
                return findProperUsers(roleName, categoryName, courseTitle, pageNumber - 1, model);
            model.addAttribute("userDtos", properUsers);
        }
        List<String> courseTitleList = courseService.findCourseTitlesByCategoryName(categoryName);
        UserDto userDto = (model.getAttribute("userDto") == null ? new UserDto() : (UserDto) model.getAttribute("userDto"));
        model.addAttribute("roleName", roleName)
                .addAttribute("categoryName", categoryName)
                .addAttribute("courseTitle", courseTitle)
                .addAttribute("courseTitles", courseTitleList)
                .addAttribute("totalPages", totalPages)
                .addAttribute("pageNumber", pageNumber)
                .addAttribute("userDto", userDto);
        return showAddParticipantForm(model);
    }

    @PostMapping(value = "addParticipant/{roleName}/{categoryName}/{courseTitle}/{pageNumber}")
    public ModelAndView addParticipant(@PathVariable(name = "roleName") String roleName,
                                       @PathVariable(name = "categoryName") String categoryName,
                                       @PathVariable(name = "courseTitle") String courseTitle,
                                       @PathVariable(name = "pageNumber") int pageNumber,
                                       @ModelAttribute UserDto userDto, Model model) {
        userService.updateUserCourses(userDto, courseTitle);
        model.addAttribute("userDto", userDto);
        return findProperUsers(roleName, categoryName, courseTitle, pageNumber, model);
    }
}