package com.example.repository;

import com.example.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    private final List<User> users = new ArrayList<>();

    public List<User> getAll() {
        return users;
    }

    public Optional<User> getById(Long id) {
        return users.stream()
            .filter(user -> user.getId().equals(id))
            .findFirst();
    }

    public void create(User user) {
        users.add(user);
    }

    public void update(Long id, User userDetails) {
        Optional<User> userFound = getById(id);
        if (userFound.isPresent()) {
            User userToUpdate = userFound.get();
            userToUpdate.setName(userDetails.getName());
            userToUpdate.setSurname(userDetails.getSurname());
            userToUpdate.setAge(userDetails.getAge());
        }
    }

    public void delete(Long id) {
        Optional<User> userFound = getById(id);
        if (userFound.isPresent()) {
            User userToDelete = userFound.get();
            users.remove(userToDelete);
        }
    }

}
