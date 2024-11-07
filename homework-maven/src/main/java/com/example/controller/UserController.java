package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;

import java.util.List;
import java.util.Optional;

public class UserController {

    private UserService userService;

    public List<User> getAll() {
        return userService.getAll();
    }

    public Optional<User> getById(Long id) {
        return userService.getById(id);
    }

    public void create(User user) {
        userService.create(user);
    }

    public void update(Long id, User userDetails) {
        userService.update(id, userDetails);
    }

    public void delete(Long id) {
        userService.delete(id);
    }

}
