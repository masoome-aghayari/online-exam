package ir.maktab.validator;

import ir.maktab.model.dto.UserDto;
import ir.maktab.service.UserService;
import ir.maktab.service.converter.UserDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Autowired
    UserService userService;
    @Autowired
    UserDtoConverter userDtoConverter;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserDto.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserDto user = (UserDto) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
        if (!user.getName().matches("^[a-zA-Z]+"))
            errors.rejectValue("name", "Alphabetic");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "family", "NotEmpty");
        if (!user.getFamily().matches("^[a-zA-Z]+"))
            errors.rejectValue("family", "Alphabetic");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        if (!user.getEmail().matches("^[\\w!#$%&'*+/=?`{|}~^-]+" +
                "(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"))
            errors.rejectValue("email", "Invalid.userDto.email");
        if (userService.findByEmail(user.getEmail()) != null)
            errors.rejectValue("email", "Duplicate.userDto.email");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 8 || user.getPassword().length() > 16)
            errors.rejectValue("password", "Size.userDto.password");
        if (!user.getPassword().matches("\\A(?=.*[A-Z])(?=.*\\d)[a-zA-Z0-9]{8,16}\\z"))
            errors.rejectValue("password",
                    "Pattern.userDto.password");
        if (!user.getPassword().matches(user.getConfirmPassword()))
            errors.rejectValue("confirmPassword", "Diff.userDto.passwordConfirm");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "role", "NotEmpty");
        if (userDtoConverter.stringToRoleConverter(user.getRole()) == null)
            errors.rejectValue("role", "Undefined.userDto.role");
    }
}
