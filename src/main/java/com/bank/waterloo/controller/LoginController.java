package com.bank.waterloo.controller;

import com.bank.waterloo.dto.LoginDTO;
import com.bank.waterloo.dto.LoginResponseDTO;
import com.bank.waterloo.exception.WaterlooException;
import com.bank.waterloo.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(value = "/auth")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Value("${msg.empty.username.password}")
    private String MSG_EMPTY_USERNAME_PASSWORD;

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) throws NoSuchAlgorithmException {
        if (StringUtils.isEmpty(loginDTO.getUserName()) || StringUtils.isEmpty(loginDTO.getPassword()))
            throw new WaterlooException(MSG_EMPTY_USERNAME_PASSWORD);
        LOGGER.info("login request received from user ");
        LoginResponseDTO loginResponseDTO = loginService.verifyUserNameAndPassword(loginDTO);
        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }
}
