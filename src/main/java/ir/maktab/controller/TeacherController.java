package ir.maktab.controller;

import ir.maktab.model.dto.CourseDto;
import ir.maktab.model.dto.ExamDto;
import ir.maktab.model.dto.UserDto;
import ir.maktab.model.entity.Status;
import ir.maktab.service.CourseService;
import ir.maktab.service.ExamService;
import ir.maktab.service.QuestionBankService;
import ir.maktab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/teacher")
@PropertySources({@PropertySource("classpath:message.properties"),
        @PropertySource("classpath:constant-numbers.properties")})
@PreAuthorize("hasAuthority('TEACHER')")
public class TeacherController {
    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;
    @Autowired
    ExamService examService;
    @Autowired
    QuestionBankService questionBankService;

    @Autowired
    Environment env;

    @GetMapping("")
    public String showDashboard() {
        return "TEACHERDashboard";
    }

    @GetMapping("courses/{pageNumber}")
    public ModelAndView showTeacherCourse(@PathVariable(name = "pageNumber") int pageNumber, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        UserDto teacherDto = (UserDto) session.getAttribute("userDto");
        long totalPages = courseService.getNumberOfTeacherCoursesPage(teacherDto);
        if (totalPages == 0)
            return new ModelAndView("teacherMessage", "message", env.getProperty("No.Course.Found"));
        List<CourseDto> teacherCourses = courseService.findTeacherCourses(teacherDto, pageNumber - 1,
                Integer.parseInt(env.getProperty("Page.Rows")));
        ModelAndView teacherCourse = new ModelAndView("showTeacherCourses");
        teacherCourse.addObject("pageNumber", pageNumber)
                .addObject("totalPages", totalPages)
                .addObject("courses", teacherCourses);
        return teacherCourse;
    }

    @GetMapping(value = "courses/{courseTitle}/exams/{pageNumber}")
    public ModelAndView showCourseExams(@PathVariable(name = "courseTitle") String courseTitle,
                                        @PathVariable(name = "pageNumber") int pageNumber, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        UserDto teacherDto = (UserDto) session.getAttribute("userDto");
        long totalPages = examService.countExamsPagesByCourseTitle(courseTitle);
        if (totalPages == 0)
            return new ModelAndView("teacherMessage", "message", env.getProperty("No.Exam.Found"));//TODO:Dashboard button
        int limit = Integer.parseInt(env.getProperty("Page.Rows"));
        List<ExamDto> examDtos = examService.findExamsByCourseTitle(courseTitle, pageNumber - 1, limit);
        ModelAndView showCourseExams = new ModelAndView("showCourseExams");
        ExamDto examDto = new ExamDto();
        examDto.setCreatorDto(teacherDto);
        showCourseExams.addObject("pageNumber", pageNumber)
                .addObject("exams", examDtos)
                .addObject("examDto", examDto);
        return showCourseExams;
    }

    @PostMapping(value = "courses/{courseTitle}/new-exam")
    public ModelAndView showCreateExamPage(@PathVariable(name = "courseTitle") String courseTitle,
                                           HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        UserDto teacherDto = (UserDto) session.getAttribute("userDto");
        ModelAndView showNewExamPage = new ModelAndView("createExam");
        ExamDto newExamDto = examService.setCreatorAndTitle(teacherDto, courseTitle);
        showNewExamPage.addObject("courseTitle", courseTitle)
                .addObject("newExam", newExamDto)
                .addObject("message", model.getAttribute("message"));
        return showNewExamPage;
    }

    @PostMapping(value = "courses/{courseTitle}/new-exam/save")
    public ModelAndView saveNewExam(@PathVariable(name = "courseTitle") String courseTitle, Model model,
                                    @ModelAttribute ExamDto newExamDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        UserDto teacherDto = (UserDto) session.getAttribute("userDto");
        examService.save(newExamDto, courseTitle);
        model.addAttribute("message", env.getProperty("Add.Exam.Successful"));
        return showCreateExamPage(courseTitle, request, model);
    }

    @PostMapping(value = "exams/edit")
    public ModelAndView showEditPage(@ModelAttribute ExamDto examDto, Model model) {
        ModelAndView editExam = new ModelAndView("editExam");
        editExam.addObject("examDto", examDto)
                .addObject("message", model.getAttribute("message"));
        return editExam;
    }

    @PostMapping(value = "exams/edit/add-question")
    public ModelAndView showChooseAddQuestionMethodPage(@ModelAttribute ExamDto examDto, Model model) {
        ModelAndView addQuestionMethod = new ModelAndView("chooseAddQuestionMethod");
        addQuestionMethod.addObject("examDto", examDto)
                .addObject("message", model.getAttribute("message"));
        return addQuestionMethod;
    }

    @GetMapping(value = "exam/add-question/new/chooseType")
    public ModelAndView showQuestionTypePage(@ModelAttribute ExamDto examDto) {
        return new ModelAndView("chooseQuestionType", "examDto", examDto);
    }

  /*  @GetMapping(value = "exam/add-question/new/test")
    public ModelAndView showAddTestQuestionPage(@ModelAttribute ExamDto examDto) {
        ModelAndView testQuestion = new ModelAndView("addTestQuestion");
        testQuestion.addObject("examDto", examDto)
                .addObject("userDto", examDto.getCreatorDto());
    }*/


    @PostMapping(value = "/exams/edit/delete")
    public ModelAndView deleteExam(@ModelAttribute ExamDto examDto, Model model) {
        examService.deleteExamByTitle(examDto.getTitle());
        model.addAttribute("message", env.getProperty("Delete.Exam.Successful"));
        return showEditPage(examDto, model);
    }

    @PostMapping(value = "/exams/stop")
    public ModelAndView stopExam(@ModelAttribute ExamDto examDto, Model model) {
        examService.changeExamStatus(examDto.getTitle(), Status.INACTIVE);
        model.addAttribute("message", env.getProperty("Stop.Exam.Successful"));
        examDto.setStatus(Status.INACTIVE.toString());
        return showEditPage(examDto, model);
    }

    @PostMapping(value = "/exams/activate")
    public ModelAndView ActivateExam(@ModelAttribute ExamDto examDto, Model model) {
        examService.changeExamStatus(examDto.getTitle(), Status.ACTIVE);
        model.addAttribute("message", env.getProperty("Activate.Exam.Successful"));
        examDto.setStatus(Status.ACTIVE.toString());
        return showEditPage(examDto, model);
    }

/*

    @PostMapping(value = "exam/add-question/new/test/process")
    public ModelAndView addNewQuestionProcess(HttpServletRequest request, @ModelAttribute QuestionDto questionDto) {
        ExamDto examDto = (ExamDto) request.getAttribute("examDto");
        examDto.getQuestionDtoMarks().put(questionDto, 0);
        request.setAttribute("examDto", examDto);
       return showAddQuestionForm(request, examDto);
    }

*/

 /*   @PostMapping(value = "exam/add-question/bank")
    public ModelAndView showBankQuestions(@ModelAttribute ExamDto examDto, Model model) {
        examDto = examService.findExamByTitle(examDto.getTitle());
        List<QuestionDto> questions = questionBankService.findQuestionsByCategoryName(examDto.getCourseDto().getCategory());
        if (Objects.isNull(questions)) {
            model.addAttribute("message", env.getProperty("Empty.Bank"));
            return showChooseAddQuestionMethodPage(examDto, model);
        }
        ModelAndView showBankQuestions = new ModelAndView("showBankQuestions");
        showBankQuestions.addObject("examDto", examDto);
        showBankQuestions.addObject("questions", questions);
        return showBankQuestions;
    }

    @PostMapping(value = "exam/add-question/bank/addProcess/{examTitle}")
    public ModelAndView addFromBankProcess(@ModelAttribute Set<QuestionDto> questionDtos,
                                           @PathVariable(name = "examTitle") String examTitle) {
        examService.updateQuestionsOfQuestionMark(examTitle, questionDtos);
        return null;
    }*/
}