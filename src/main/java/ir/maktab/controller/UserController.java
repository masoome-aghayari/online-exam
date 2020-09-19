package ir.maktab.controller;

import ir.maktab.exceptions.EmailNotSendException;
import ir.maktab.model.dto.ConfirmationTokenDto;
import ir.maktab.model.dto.UserDto;
import ir.maktab.model.entity.Status;
import ir.maktab.service.ConfirmationTokenService;
import ir.maktab.service.MailService;
import ir.maktab.service.RoleService;
import ir.maktab.service.UserService;
import ir.maktab.service.converter.UserDtoConverter;
import ir.maktab.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

@Slf4j
@Controller
@PropertySource("classpath:message.properties")
public class UserController {
    @Autowired
    ApplicationEventPublisher eventPublisher;
    @Autowired
    public UserService userService;
    @Autowired
    public RoleService roleService;
    @Autowired
    public ConfirmationTokenService confirmationTokenService;
    @Autowired
    UserDtoConverter userDtoConverter;
    @Autowired
    UserValidator userValidator;
    @Autowired
    Environment env;

    @GetMapping(value = "/")
    public ModelAndView showHomePage() {
        return new ModelAndView("home");
    }

    @GetMapping(value = "/register")
    public ModelAndView showRegisterPage() {
        List<String> userRoles = roleService.getUserRoles();
        ModelAndView register = new ModelAndView("register");
        register.addObject("roles", userRoles)
                .addObject("userDto", new UserDto());
        return register;
    }

    @PostMapping(value = "/registerProcess")
    public String registerUser(UserDto userDto, Model model, BindingResult bindingResult) {
        model.addAttribute("roles", roleService.getUserRoles());
        userValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "register";
        }
//        User user = userDtoConverter.convertToSaveUser(userDto);
        userDto = userService.registerNewUser(userDto);
        ModelAndView result = new ModelAndView("result");
        String resultMessage;
        try {
            sendMail(userDto);
            resultMessage = env.getProperty("Email.Sent.Successfully") + userDto.getEmail();
        } catch (EmailNotSendException e) {
            resultMessage = e.getMessage();
        }
        model.addAttribute("message", resultMessage)
                .addAttribute("userDto", userDto);
        return "result";
    }

    @PostMapping(value = "/resendProcess")
    public String resendProcess(@ModelAttribute("userDto") UserDto userDto, Model model) {
        Status userStatus = userService.findUserStatus(userDto.getEmail());
        if (userStatus.equals(Status.INACTIVE)) {
            try {
                confirmationTokenService.deleteTokenByUserEmail(userDto.getEmail());
                sendMail(userDto);
                model.addAttribute("message", env.getProperty("Resend.Email.Successful") + userDto.getEmail());
            } catch (EmailNotSendException e) {
                model.addAttribute("message", e.getMessage());
            }
            model.addAttribute("resendStatus", false);
        } else {
            model.addAttribute("message", env.getProperty("Verification.Duplicate"))
                    .addAttribute("resendStatus", true);
        }
        return "result";
    }

    @GetMapping("/verify-account")
    public String verifyRegistration(Model model, @RequestParam("token") String token) {
        try {
            ConfirmationTokenDto requestedToken = confirmationTokenService.findByToken(token);
            UserDto userDto = requestedToken.getUserDto();
            Calendar calendar = Calendar.getInstance();
            if ((requestedToken.getExpiryDate().getTime() - calendar.getTime().getTime()) <= 0) {
                model.addAttribute("errorMessage", env.getProperty("Token.Expired"));
                return "error";
            }
            userService.updateUserStatus(userDto.getEmail(), Status.PENDING);
            confirmationTokenService.deleteTokenByUserEmail(userDto.getEmail());
            model.addAttribute("message", env.getProperty("Verification.Successful"));
            model.addAttribute("userDto", new UserDto());
            return "login";
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @GetMapping(value = "/login")
    public ModelAndView showLoginPage(Model model) {
        ModelAndView login = new ModelAndView("login");
        login.addObject("userDto", new UserDto());
        login.addObject("message", model.getAttribute("message"));
        return login;
    }

    @PostMapping(value = "/successProcess")
    public ModelAndView loginProcess(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String email = request.getParameter("email");
        UserDto desiredUser = userService.findByEmail(email);
        session.setAttribute("userDto", desiredUser);
        ModelAndView dashboard = new ModelAndView(desiredUser.getRole() + "Dashboard");
        dashboard.addObject("userDto", desiredUser);
        return dashboard;
    }

    @GetMapping(value = "loginError")
    public ModelAndView showLoginError(Model model) {
        model.addAttribute("message", env.getProperty("Login.BadCredentials"));
        return showLoginPage(model);
    }

    private void sendMail(UserDto userDto) throws EmailNotSendException {
        ConfirmationTokenDto newDtoToken = new ConfirmationTokenDto();
        newDtoToken.setUserDto(userDto);
        String token = confirmationTokenService.saveToken(newDtoToken);
        String mailText = env.getProperty("Email.Verification.Text") + token;
        try {
            MailService.sendMail(userDto.getEmail(), env.getProperty("Email.Verification.Subject"), mailText);
        } catch (MessagingException | IOException e) {
            throw new EmailNotSendException(env.getProperty("Sending.Email.Failed"));
        }
    }
/*
    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public ModelAndView getAllUsers(HttpServletRequest request, HttpServletResponse response) {
        List<UserDto> users = userService.findAll();
        return new ModelAndView("showAllUsers", "users", users);
    }*/
/*
    @RequestMapping(value = "/searchResult", method = RequestMethod.GET)
    public ModelAndView searchUsers(@ModelAttribute("user") User user) {
        Optional<User> desiredUser =
                userService.findByEmail(user.getEmail());
        ModelAndView mav;
        if (desiredUser.isPresent()) {
            user = desiredUser.get();
            mav = new ModelAndView("showSearchResult", "user", user);
        } else
            mav = new ModelAndView("error",
                    "errorMessage",
                    "Not Found");
        return mav;
    }*/
}