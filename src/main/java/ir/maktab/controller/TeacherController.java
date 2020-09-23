package ir.maktab.controller;

import ir.maktab.model.dto.CourseDto;
import ir.maktab.model.dto.ExamDto;
import ir.maktab.model.dto.QuestionDto;
import ir.maktab.model.dto.UserDto;
import ir.maktab.model.entity.QuestionType;
import ir.maktab.model.entity.Status;
import ir.maktab.service.*;
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
import java.util.*;

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
    QuestionService questionService;
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
            return new ModelAndView("teacherMessage", "message", env.getProperty("No.Exam.Found"));
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
    public ModelAndView showEditPage(@ModelAttribute ExamDto examDto, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.setAttribute("examDto", examDto);
        ModelAndView editExam = new ModelAndView("editExam");
        editExam.addObject("examDto", examDto)
                .addObject("message", model.getAttribute("message"));
        return editExam;
    }

    @GetMapping(value = "exams/edit/add-question")
    public ModelAndView showChooseAddQuestionMethodPage(Model model) {
        ModelAndView addQuestionMethod = new ModelAndView("chooseAddQuestionMethod");
        addQuestionMethod.addObject("message", model.getAttribute("message"));
        return addQuestionMethod;
    }

    @GetMapping(value = "exam/add-question/new/chooseType")
    public ModelAndView showQuestionTypePage() {
        return new ModelAndView("chooseQuestionType");
    }

    @GetMapping(value = "exam/add-question/new/test")
    public ModelAndView showAddTestQuestionPage(Model model) {
        ModelAndView testQuestion = new ModelAndView("createTestQuestion");
        QuestionDto questionDto = new QuestionDto();
        questionDto.setType(QuestionType.TEST.name());
        testQuestion.addObject("questionDto", questionDto)
                .addObject("message", model.getAttribute("message"));
        return testQuestion;
    }

    @GetMapping(value = "exam/add-question/new/essay")
    public ModelAndView showAddEssayQuestionPage(Model model) {
        ModelAndView essayQuestion = new ModelAndView("createEssayQuestion");
        QuestionDto questionDto = new QuestionDto();
        questionDto.setType(QuestionType.ESSAY.name());
        essayQuestion.addObject("questionDto", questionDto)
                .addObject("message", model.getAttribute("message"));
        return essayQuestion;
    }

    @PostMapping(value = "exam/add-question/new/process")
    public ModelAndView addTestQuestionToExam(HttpServletRequest request, @ModelAttribute QuestionDto questionDto,
                                              Model model) {
        HttpSession session = request.getSession(false);
        ExamDto examDto = (ExamDto) session.getAttribute("examDto");
        if (questionDto.getType().equals(QuestionType.TEST.name())) {
            List<String> options = Arrays.asList(request.getParameterValues("option"));
            questionDto.setOptions(options);
        }
        int mark = Integer.parseInt(request.getParameter("mark"));
        if (examDto.getQuestionDtoMarks() == null)
            examDto.setQuestionDtoMarks(new HashMap<>());
        examDto.getQuestionDtoMarks().put(questionDto, mark);
        questionDto.setExams(new ArrayList<>());
        questionDto.getExams().add(examDto);
        boolean isExists = questionService.addQuestion(questionDto, examDto, mark);
        if (isExists)
            model.addAttribute("message", env.getProperty("Question.Exists"));
        else {
            session.setAttribute("examDto", examDto);
            model.addAttribute("message", env.getProperty("Question.Add.Successful"));
        }
        if (questionDto.getType().equals(QuestionType.TEST.name()))
            return showAddTestQuestionPage(model);
        else
            return showAddEssayQuestionPage(model);
    }

    @GetMapping(value = "exam/add-question/bank")
    public ModelAndView showBankQuestions(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        ExamDto examDto = (ExamDto) session.getAttribute("examDto");
        List<QuestionDto> questions = questionBankService.findQuestionsByCategoryName(examDto);
        if (Objects.isNull(questions)) {
            model.addAttribute("message", env.getProperty("Empty.Bank"));
        } else if (questions.isEmpty()) {
            model.addAttribute("message", env.getProperty("No.More.Question.In.Bank"));
        } else {
            ModelAndView showBankQuestions = new ModelAndView("showBankQuestions");
            showBankQuestions.addObject("questions", questions)
                    .addObject("questionDto", new QuestionDto());
            return showBankQuestions;
        }
        return showChooseAddQuestionMethodPage(model);
    }

    @PostMapping(value = "exam/add-question/bank/process")
    public ModelAndView addFromBankProcess(@ModelAttribute QuestionDto questionDto, HttpServletRequest request,
                                           Model model) {
        HttpSession session = request.getSession(false);
        ExamDto examDto = (ExamDto) session.getAttribute("examDto");
        int mark = Integer.parseInt(request.getParameter("mark"));
        examService.updateExamQuestions(examDto.getTitle(), questionDto, mark);
        if (examDto.getQuestionDtoMarks() == null)
            examDto.setQuestionDtoMarks(new HashMap<>());
        examDto.getQuestionDtoMarks().put(questionDto, mark);
        session.setAttribute("examDto", examDto);
        return showBankQuestions(model, request);
    }

    @PostMapping(value = "exams/edit/delete")
    public ModelAndView deleteExam(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        ExamDto examDto = (ExamDto) session.getAttribute("examDto");
        examService.deleteExamByTitle(examDto.getTitle());
        model.addAttribute("message", env.getProperty("Delete.Exam.Successful"));
        session.removeAttribute("examDto");
        return showCourseExams(examDto.getCourseDto().getTitle(), 1, request);
    }

    @PostMapping(value = "exams/stop")
    public ModelAndView stopExam(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        ExamDto examDto = (ExamDto) session.getAttribute("examDto");
        examService.changeExamStatus(examDto.getTitle(), Status.INACTIVE);
        model.addAttribute("message", env.getProperty("Stop.Exam.Successful"));
        examDto.setStatus(Status.INACTIVE.toString());
        session.setAttribute("examDto", examDto);
        return showEditPage(examDto, model, request);
    }

    @PostMapping(value = "exams/activate")
    public ModelAndView ActivateExam(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        ExamDto examDto = (ExamDto) session.getAttribute("examDto");
        examService.changeExamStatus(examDto.getTitle(), Status.ACTIVE);
        model.addAttribute("message", env.getProperty("Activate.Exam.Successful"));
        examDto.setStatus(Status.ACTIVE.toString());
        session.setAttribute("examDto", examDto);
        return showEditPage(examDto, model, request);
    }
}