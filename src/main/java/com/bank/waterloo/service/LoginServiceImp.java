package com.bank.waterloo.service;

import com.bank.waterloo.dto.LoginDTO;
import com.bank.waterloo.dto.LoginResponseDTO;
import com.bank.waterloo.exception.WaterlooException;
import com.bank.waterloo.model.User;
import com.bank.waterloo.model.UserStatus;
import com.bank.waterloo.security.JwtUtil;
import com.bank.waterloo.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class LoginServiceImp implements LoginService {

    @Value("${msg.invalid.username.password}")
    private String MSG_INVALID_USERNAME_PASSWORD;

    @Value("${msg.block.user}")
    private String MSG_BLOCK_USER;

    @Value("${incorrect.password.entry.count}")
    private Integer INCORRECT_PSWD_ENTRY_COUNT;

    @Autowired
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServiceImp.class);

    @Override
    public LoginResponseDTO verifyUserNameAndPassword(LoginDTO loginDTO) throws NoSuchAlgorithmException {
        User user = userService.findByUserName(loginDTO.getUserName());
        if (user == null)
            throw new WaterlooException(MSG_INVALID_USERNAME_PASSWORD);
        if (user.getStatus().equals(UserStatus.BLOCKED.toString())) {
            throw new WaterlooException(MSG_BLOCK_USER);
        }
        String password = CommonUtil.sha256Password(loginDTO.getPassword());
        User currentUser = userService.findByUserNameAndPassword(loginDTO.getUserName(), password);
        if (currentUser == null) {
            updateUserIncorrectPasswordCount(user);
            throw new WaterlooException(MSG_INVALID_USERNAME_PASSWORD);
        }
        if (user.getInCorrectPasswordCount() != 0)
            setToActiveUser(user);

        String token = JwtUtil.generate(user);
        return new LoginResponseDTO(user.getId(), token);
    }


    private void updateUserIncorrectPasswordCount(User user) {
        if (user.getStatus().equals(UserStatus.ACTIVE.toString())
                || user.getInCorrectPasswordCount() < INCORRECT_PSWD_ENTRY_COUNT) {

            int count = user.getInCorrectPasswordCount() + 1;
            user.setInCorrectPasswordCount(count);
            if (count == INCORRECT_PSWD_ENTRY_COUNT) {
                LOGGER.info("User status changed to BLOCKED {}", user.getId());
                user.setStatus(UserStatus.BLOCKED.toString());
            }

            userService.save(user);
        }
    }

    private void setToActiveUser(User user) {
        user.setStatus(UserStatus.ACTIVE.toString());
        user.setInCorrectPasswordCount(0);
        LOGGER.info("User status revert to ACTIVE {}", user.getId());
        userService.save(user);
    }

}
