package com.bank.waterloo.service;

import com.bank.waterloo.dto.LoginDTO;
import com.bank.waterloo.dto.LoginResponseDTO;

import java.security.NoSuchAlgorithmException;

public interface LoginService {

     LoginResponseDTO verifyUserNameAndPassword(LoginDTO loginDTO) throws NoSuchAlgorithmException;
}
