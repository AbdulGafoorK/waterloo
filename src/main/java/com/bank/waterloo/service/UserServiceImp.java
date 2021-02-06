package com.bank.waterloo.service;

import com.bank.waterloo.model.User;
import com.bank.waterloo.model.UserStatus;
import com.bank.waterloo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUserNameAndPassword(String userName, String password) {
        return userRepository.findByNameAndPassword(userName, password);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByName(userName);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getOne(id);

    }
}
