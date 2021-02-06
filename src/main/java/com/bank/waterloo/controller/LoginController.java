package com.bank.waterloo.controller;

import com.bank.waterloo.dto.LoginDTO;
import com.bank.waterloo.dto.LoginResponseDTO;
import com.bank.waterloo.service.LoginService;
import com.bank.waterloo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping(value = "/auth")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) throws NoSuchAlgorithmException {
        LOGGER.info("login request received from user ");
        LoginResponseDTO loginResponseDTO = loginService.verifyUserNameAndPassword(loginDTO);
        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }
}
