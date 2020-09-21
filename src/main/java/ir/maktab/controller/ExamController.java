package ir.maktab.controller;

import org.springframework.context.annotation.PropertySource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin/course")
@PropertySource("classpath:message.properties")
@PreAuthorize("hasAuthority('TEACHER')")
public class ExamController {

}
