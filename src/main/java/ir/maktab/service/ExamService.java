package ir.maktab.service;

import ir.maktab.model.dto.ExamDto;
import ir.maktab.model.dto.QuestionDto;
import ir.maktab.model.dto.UserDto;
import ir.maktab.model.entity.Exam;
import ir.maktab.model.entity.Question;
import ir.maktab.model.entity.Status;
import ir.maktab.model.repository.ExamRepository;
import ir.maktab.model.repository.QuestionRepository;
import ir.maktab.service.converter.ExamConverter;
import ir.maktab.service.converter.QuestionDtoConverter;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@PropertySources({@PropertySource("classpath:message.properties"),
        @PropertySource("classpath:constant-numbers.properties")})
public class ExamService {
    @Autowired
    ExamRepository examRepository;
    @Autowired
    ExamConverter examConverter;
    @Autowired
    CourseService courseService;
    @Autowired
    QuestionService questionService;
    @Autowired
    QuestionDtoConverter questionDtoConverter;
    @Autowired
    Environment env;
    @Autowired
    QuestionRepository questionRepository;

    @Transactional
    public void save(ExamDto examDto, String courseTitle) {
        Exam exam = examConverter.convertDtoToSaveExam(examDto, courseTitle);
        examRepository.save(exam);
    }

    @Transactional
    public List<ExamDto> findExamsByCourseTitle(String courseTitle, int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.Direction.DESC, "startDate", "endDate");
        Page<Exam> exams = examRepository.findAllByCourse(courseTitle, pageable);
        return examConverter.convertPageOfExamsToDtoList(exams);
    }

    @Transactional
    public long countExamsPagesByCourseTitle(String courseTitle) {
        long totalExams = examRepository.countByCourseTitle(courseTitle);
        int rowsNumberInPage = Integer.parseInt(env.getProperty("Page.Rows"));
        double pages = (double) totalExams / rowsNumberInPage;
        return (int) Math.ceil(pages);
    }

    @Transactional
    public int countExamsByCourseTitle(String courseTitle) {
        return examRepository.countExamsByCourseTitle(courseTitle);
    }

    @Transactional
    public ExamDto setCreatorAndTitle(UserDto creatorDto, String courseTitle) {
        int examNumber = countExamsByCourseTitle(courseTitle);
        ExamDto newExamDto = new ExamDto();
        newExamDto.setCreatorDto(creatorDto);
        newExamDto.setTitle(courseTitle + "-exam" + (++examNumber));
        return newExamDto;
    }

    @Transactional
    public void deleteExamByTitle(String examTitle) {
        examRepository.deleteExamByTitle(examTitle);
    }

    @Transactional
    public void deleteByCourseTitle(String courseTitle) {
        examRepository.deleteByCourseTitle(courseTitle);
    }

    @Transactional
    public void changeExamStatus(String title, Status status) {
        examRepository.updateStatusByTitle(title, status);
    }

    @Transactional
    public ExamDto findExamByTitle(String title) {
        return examConverter.convertToExamDto(examRepository.findByTitle(title));
    }

    public void updateExamQuestions(String title, QuestionDto questionDto, int mark) {
        Exam exam = examRepository.findByTitle(title);
        Optional<Question> question = questionRepository.findByText(questionDto.getText());
        Map<Question, Integer> questionMarks = exam.getQuestionMarks();
        if (questionMarks == null)
            questionMarks = new HashMap<>();
        if (question.isPresent())
            questionMarks.put(question.get(), mark);
        examRepository.save(exam);
    }
/*
    @Transactional
    public void updateQuestionsOfQuestionMark(String examTitle, Set<QuestionDto> questionDtos) {
        Exam exam = examRepository.findByTitle(examTitle);
        Set<Question> questions = questionDtoConverter.convertDtoSetToQuestionSet(questionDtos);
        Set<QuestionMark> questionMarks = setExamAndQuestions(questions, exam);
        exam.setQuestionMarks(questionMarks);
        examRepository.save(exam);
    }

    private Set<QuestionMark> setExamAndQuestions(Set<Question> questions, Exam exam) {
        for (Question q : questions) {
            QuestionMark questionMark = new QuestionMark();
            questionMark.setQuestion(q);
            questionMark.setExam(exam);
            exam.getQuestionMarks().add(questionMark);
        }
        return exam.getQuestionMarks();
    }*/
}
