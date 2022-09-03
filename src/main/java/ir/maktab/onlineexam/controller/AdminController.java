package ir.maktab.onlineexam.controller;

import ir.maktab.onlineexam.model.dto.ErrorModel;
import ir.maktab.onlineexam.model.dto.ResponseModel;
import ir.maktab.onlineexam.model.dto.SearchResultModel;
import ir.maktab.onlineexam.model.dto.UserDto;
import ir.maktab.onlineexam.model.entity.Status;
import ir.maktab.onlineexam.service.RoleService;
import ir.maktab.onlineexam.service.UserService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "${admin.base.url}")
@PropertySource("classpath:message.properties")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final Environment env;

    public AdminController(UserService userService, RoleService roleService, Environment env) {
        this.userService = userService;
        this.roleService = roleService;
        this.env = env;
    }

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

    @PostMapping(value = "search/{pageNumber}")
    public ResponseModel search(@ModelAttribute UserDto userDto, @PathVariable(required = false) int pageNumber) {
        long totalPages = userService.getTotalNumberOfPages(userDto);
        ResponseModel responseModel;
        if (totalPages == 0) {
            responseModel = getErrorResponseModel();
        } else {
            int limit = Integer.parseInt(env.getProperty("Page.Rows"));
            List<UserDto> matchedUsers = userService.findMaxMatch(userDto, pageNumber - 1, limit);
            List<String> userRoles = roleService.getUserRoles();
            responseModel = getSearchResultResponseModel(userDto, pageNumber, totalPages, matchedUsers, userRoles);
        }
        return responseModel;
    }

    private ResponseModel<SearchResultModel> getSearchResultResponseModel(UserDto userDto,
                                                                          int pageNumber,
                                                                          long totalPages,
                                                                          List<UserDto> matchedUsers,
                                                                          List<String> userRoles) {
        ResponseModel<SearchResultModel> responseModel = new ResponseModel<>();
        SearchResultModel searchResultModel = buildSearchResultModel(userDto, pageNumber, totalPages, matchedUsers, userRoles);
        responseModel.setData(searchResultModel);
        return responseModel;
    }

    private SearchResultModel buildSearchResultModel(UserDto userDto, int pageNumber,
                                                     long totalPages, List<UserDto> matchedUsers,
                                                     List<String> userRoles) {
        return SearchResultModel.builder()
                .matchedUsers(matchedUsers)
                .pageNumber(pageNumber)
                .totalPages(totalPages)
                .userDto(userDto)
                .roles(userRoles)
                .build();
    }

    private ResponseModel<ErrorModel> getErrorResponseModel() {
        ResponseModel<ErrorModel> responseModel = new ResponseModel<>();
        ErrorModel data = new ErrorModel(env.getProperty("No.User.Found"), HttpStatus.OK);
        return responseModel;
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