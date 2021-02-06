package com.bank.waterloo.service;


import com.bank.waterloo.model.User;

public interface UserService {

    User findByUserNameAndPassword(String userName, String password);

    User findByUserName(String userName);

    User save(User user);

    User getUserById(Long id);
}
