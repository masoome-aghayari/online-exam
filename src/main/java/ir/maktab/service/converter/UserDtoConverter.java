package ir.maktab.service.converter;

import ir.maktab.model.dto.UserDto;
import ir.maktab.model.entity.Role;
import ir.maktab.model.entity.Status;
import ir.maktab.model.entity.User;
import ir.maktab.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDtoConverter {
    @Autowired
    RoleService roleService;

    public User convertToSaveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setFamily(userDto.getFamily());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setRole(stringToRoleConverter(userDto.getRole()));
        user.setStatus(Status.INACTIVE);
        return user;
    }

    public UserDto convertUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setFamily(user.getFamily());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().getRoleName());
        userDto.setStatus(user.getStatus());
        userDto.setCourses(user.getCourses());
        return userDto;
    }

    public Role stringToRoleConverter(String roleName) {
        return roleService.getRoleByName(roleName);
    }

    public ArrayList<UserDto> convertUserListToDtoList(List<User> pendingUsers) {
        return pendingUsers.stream().map(this::convertUserToDto).collect(Collectors.toCollection(ArrayList::new));
    }

    public Page<UserDto> convertUserPageToDtoPage(Page<User> userPage) {
        return new PageImpl<>(convertUserListToDtoList(userPage.getContent()));
    }
}