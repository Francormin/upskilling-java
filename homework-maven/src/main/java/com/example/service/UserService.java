package com.example.service;

import com.example.model.User;
import com.example.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService {

    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public Optional<User> getById(Long id) {
        return userRepository.getById(id);
    }

    public void create(User user) {
        userRepository.create(user);
    }

    public void update(Long id, User userDetails) {
        userRepository.update(id, userDetails);
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }

}
