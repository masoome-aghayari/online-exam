package ir.maktab.onlineexam.service;

import ir.maktab.onlineexam.model.dto.ConfirmationTokenDto;
import ir.maktab.onlineexam.model.entity.ConfirmationToken;
import ir.maktab.onlineexam.model.entity.User;
import ir.maktab.onlineexam.model.repository.ConfirmationTokenRepository;
import ir.maktab.onlineexam.service.converter.ConfirmationTokenConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@PropertySource("classpath:message.properties")
public class ConfirmationTokenService {
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    ConfirmationTokenConverter confirmationTokenConverter;
    @Autowired
    UserService userService;
    @Autowired
    Environment env;

    @Transactional
    public String saveToken(ConfirmationTokenDto dtoToken) {
        ConfirmationToken token = confirmationTokenConverter.convertToSaveToken(dtoToken);
        confirmationTokenRepository.save(token);
        return token.getToken();
    }

    @Transactional
    public boolean isValid(User user) {
        return confirmationTokenRepository.findByUser(user).isPresent();
    }

    @Transactional
    public ConfirmationTokenDto findByToken(String token) throws EntityNotFoundException {
        Optional<ConfirmationToken> desiredToken = confirmationTokenRepository.findByToken(token);
        if (desiredToken.isPresent())
            return confirmationTokenConverter.convertToDto(desiredToken.get());
        else
            throw new EntityNotFoundException(env.getProperty("Invalid.Link"));
    }

    @Transactional
    public void deleteTokenByUserEmail(String email) {
        User desiredUser = userService.findUserByEmail(email);
        confirmationTokenRepository.deleteByUser(desiredUser);
    }

    @Transactional
    public void deleteByToken(String token) {
        confirmationTokenRepository.deleteByToken(token);
    }
}