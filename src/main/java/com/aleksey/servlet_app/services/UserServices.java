package com.aleksey.servlet_app.services;

import com.aleksey.servlet_app.model.User;
import com.aleksey.servlet_app.repository.UserRepository;
import com.aleksey.servlet_app.repository.hibernate_db.UserRepositoryImpl;

import java.util.List;

public class UserServices {

    private UserRepository userRepository;

    public UserServices() {
        this.userRepository = new UserRepositoryImpl();
    }

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.create(user);
    }

    public User updateUser(User user) {
        return userRepository.update(user);
    }

    public List<User> getAllUser() {
        return userRepository.readAll();
    }

    public User getUserById(Integer id) {
        return userRepository.readById(id);
    }

    public void deleteUserById(Integer id) {
        userRepository.delete(id);
    }
}
