package com.aleksey.servlet_app.controller;

import com.aleksey.servlet_app.model.User;
import com.aleksey.servlet_app.services.UserServices;

import java.util.List;

public class UserController {

    private UserServices userServices;

    public UserController() {
        this.userServices = new UserServices();
    }

    public User createUser(User user) {
        return userServices.createUser(user);
    }

    public User updateUser(User user) {
        return userServices.updateUser(user);
    }

    public List<User> getAllUser() {
        return userServices.getAllUser();
    }

    public User getUserById(Integer id) {
        return userServices.getUserById(id);
    }

    public void deleteUserById(Integer id) {
        userServices.deleteUserById(id);
    }
}
