package ir.maktab.controller;

import ir.maktab.model.dto.UserDto;
import ir.maktab.model.entity.Status;
import ir.maktab.service.RoleService;
import ir.maktab.service.UserService;
import ir.maktab.service.converter.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
@PropertySource("classpath:message.properties")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    UserDtoConverter userDtoConverter;
    @Autowired
    Environment env;

    @GetMapping(value = "")
    public ModelAndView adminDashboard(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("ADMINDashboard");
    }

    @GetMapping(value = "pending-list/{pageNumber}")
    public ModelAndView getPendingUsers(HttpServletRequest request, @PathVariable(required = false) int pageNumber) {
        if (pageNumber < 1)
            pageNumber = 1;
        int totalPages = userService.findNumberOfPendingUsers(Status.PENDING);
        if (pageNumber > totalPages)
            pageNumber = totalPages;
        int limit = Integer.parseInt(env.getProperty("Page.Rows"));
        List<String> roles = roleService.getUserRoles();
        List<UserDto> pendingList = userService.findAllPending(pageNumber - 1, limit).getContent();
        if (pageNumber > 1 && pendingList.isEmpty())
            return getPendingUsers(request, pageNumber - 1);
        if (pendingList.isEmpty())
            return new ModelAndView("message", "message", env.getProperty("No.Pending.Found"));
        ModelAndView showPendingList = new ModelAndView("showPendingList");
        showPendingList.addObject("pageNumber", pageNumber)
                .addObject("totalPages", totalPages)
                .addObject("users", pendingList)
                .addObject("userDto", new UserDto())
                .addObject("roles", roles);
        return showPendingList;
    }

    @GetMapping(value = "confirm-user/{pageNumber}")
    public ModelAndView confirmUser(HttpServletRequest request, @ModelAttribute("userDto") UserDto userDto,
                                    @PathVariable(required = false) int pageNumber) {
        userService.updateWhenConfirm(userDto);
        return getPendingUsers(request, pageNumber);
    }

    @GetMapping(value = "searchProcess/{pageNumber}")
    public ModelAndView searchProcess(@ModelAttribute UserDto userDto, @PathVariable(required = false) int pageNumber) {
        long totalPages = userService.getTotalNumberOfPages(userDto);
        if (totalPages == 0)
            return new ModelAndView("message", "message", env.getProperty("No.User.Found"));
        int limit = Integer.parseInt(env.getProperty("Page.Rows"));
        List<UserDto> matchedUsers = userService.findMaxMatch(userDto, pageNumber - 1, limit);
        ModelAndView searchUser = new ModelAndView("searchUser");
        searchUser.addObject("users", matchedUsers)
                .addObject("roles", roleService.getUserRoles())
                .addObject("pageNumber", pageNumber)
                .addObject("totalPages", totalPages)
                .addObject("userDto", userDto);
        return searchUser;
    }

    @PostMapping(value = "saveChanges", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String editUser(@RequestBody UserDto userDto) {
        userService.updateUser(userDto);
        return env.getProperty("Update.Successful");
    }

  /*@PostMapping(value = "confirm-all")  TODO
    public String confirmAll(@ModelAttribute("userListWrapper") UserListWrapper userListWrapper, Model model) {
        ArrayList<UserDto> users = userListWrapper.getUsers();
        for (UserDto user : users) {
            confirmUser(user, model);
        }
        return getPendingUsers(model);
    }*/
  /* @RequestParam(required = false, name = "name") String name,
                                @RequestParam(required = false, name = "family") String family,
                                @RequestParam(required = false, name = "email") String email,
                                @RequestParam(required = false, name = "role") String role,*/
}