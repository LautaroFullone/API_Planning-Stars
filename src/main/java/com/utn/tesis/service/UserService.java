package com.utn.tesis.service;

import com.utn.tesis.model.User;
import com.utn.tesis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user) {
        User u = userRepository.save(user);
        return u;
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }
}
