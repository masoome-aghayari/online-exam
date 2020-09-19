package ir.maktab.service.converter;

import ir.maktab.model.dto.ConfirmationTokenDto;
import ir.maktab.model.dto.UserDto;
import ir.maktab.model.entity.ConfirmationToken;
import ir.maktab.model.entity.User;
import ir.maktab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationTokenConverter {
    @Autowired
    CourseDtoConverter courseDtoConverter;
    @Autowired
    UserDtoConverter userDtoConverter;
    @Autowired
    UserService userService;

    public ConfirmationToken convertToSaveToken(ConfirmationTokenDto dtoToken) {
        User user = userService.findUserByEmail(dtoToken.getUserDto().getEmail());
        return new ConfirmationToken(user);
    }

    public ConfirmationTokenDto convertToDto(ConfirmationToken token) {
        UserDto userDto = userDtoConverter.convertUserToDto(token.getUser());
        ConfirmationTokenDto dtoToken = new ConfirmationTokenDto();
        dtoToken.setUserDto(userDto);
        dtoToken.setCreatedDate(token.getCreatedDate());
        dtoToken.setExpiryDate(token.getExpiryDate());
        dtoToken.setToken(token.getToken());
        return dtoToken;
    }
}
