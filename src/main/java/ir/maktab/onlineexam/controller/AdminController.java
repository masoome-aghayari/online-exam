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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
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

    @GetMapping(value = "/dashboard")
    public void getDashboard(HttpServletResponse response) throws IOException {
        response.sendRedirect("ADMINDashboard");
    }

    @GetMapping(value = "/pending-list/{pageNumber}")
    public ResponseModel getPendingUsers(HttpServletRequest request, @PathVariable(required = false) int pageNumber) {
        ResponseModel responseModel;
        if (pageNumber < 1)
            pageNumber = 1;
        int totalPages = userService.findNumberOfPendingUsers(Status.PENDING);
        if (pageNumber > totalPages)
            pageNumber = totalPages;
        int limit = Integer.parseInt(env.getProperty("rows.per.page"));
        List<String> roles = roleService.getUserRoles();
        List<UserDto> pendingList = userService.findAllPending(pageNumber - 1, limit).getContent();
        if (pageNumber > 1 && pendingList.isEmpty())
            responseModel = getPendingUsers(request, pageNumber - 1);
        else if (pendingList.isEmpty())
            responseModel = getErrorResponseModel("no.pending.user.found", HttpStatus.OK);
        else {
            responseModel = getSearchResultResponseModel(new UserDto(), pageNumber, totalPages, pendingList, roles);
        }
        return responseModel;
    }

    @PostMapping(value = "/confirm/user/{pageNumber}")
    public ResponseModel confirmUser(HttpServletRequest request, @ModelAttribute("userDto") UserDto userDto,
                                     @PathVariable(required = false) int pageNumber) {
        userService.updateWhenConfirm(userDto);
        return getPendingUsers(request, pageNumber);
    }

    @PostMapping(value = "/search/{pageNumber}")
    public ResponseModel search(@ModelAttribute UserDto userDto, @PathVariable(required = false) int pageNumber) {
        long totalPages = userService.getTotalNumberOfPages(userDto);
        ResponseModel responseModel;
        if (totalPages == 0) {
            responseModel = getErrorResponseModel("no.user.found", HttpStatus.OK);
        } else {
            int limit = Integer.parseInt(env.getProperty("rows.per.page"));
            List<UserDto> matchedUsers = userService.findMaxMatch(userDto, pageNumber - 1, limit);
            List<String> userRoles = roleService.getUserRoles();
            responseModel = getSearchResultResponseModel(userDto, pageNumber, totalPages, matchedUsers, userRoles);
        }
        return responseModel;
    }

    @PostMapping(value = "/update/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseModel<String> editUser(@RequestBody UserDto userDto) {
        userService.updateUser(userDto);
        ResponseModel<String> responseModel = new ResponseModel<>();
        responseModel.setData(env.getProperty("update.successful"));
        return responseModel;

    }

    @GetMapping(value = "/confirm/all")
    public ResponseModel<Boolean> confirmAll() {
        userService.confirmAllPendingUsers();
        return new ResponseModel<>(true);
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

    private ResponseModel<ErrorModel> getErrorResponseModel(String propertyName, HttpStatus status) {
        ResponseModel<ErrorModel> responseModel = new ResponseModel<>();
        ErrorModel data = new ErrorModel(env.getProperty(propertyName), status);
        responseModel.setData(data);
        return responseModel;
    }
}