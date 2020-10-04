package com.softuni.springintroex.services.impl;

import com.softuni.springintroex.entities.usersystem.User;
import com.softuni.springintroex.repositories.UserRepository;
import com.softuni.springintroex.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void registerUser(User user) {
        this.userRepository.saveAndFlush(user);
    }
}
