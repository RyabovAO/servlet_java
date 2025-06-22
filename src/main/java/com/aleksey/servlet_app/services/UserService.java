package com.aleksey.servlet_app.services;

import com.aleksey.servlet_app.entity.FileEntity;
import com.aleksey.servlet_app.entity.UserEntity;
import com.aleksey.servlet_app.repository.UserRepository;
import com.aleksey.servlet_app.repository.hibernate_db.UserRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepositoryImpl();
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity createUser(UserEntity user) {
        if(user.getName().isEmpty() || user.getName() == null) return null;
        return userRepository.create(user);
    }

    public UserEntity updateUser(UserEntity user) {
        if(user.getId() == null || user.getName().isEmpty() || user.getName() == null) return null;
        return userRepository.update(user);
    }

    public List<UserEntity> getAllUser() {
        List<UserEntity> list = userRepository.readAll();
        return !list.isEmpty() ? list : new ArrayList<>();
    }

    public UserEntity getUserById(Integer id) {
        return userRepository.readById(id);
    }

    public void deleteUserById(Integer id) {
        userRepository.delete(id);
    }
}
