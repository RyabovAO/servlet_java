package com.aleksey;

import com.aleksey.servlet_app.controller.EventController;
import com.aleksey.servlet_app.controller.UserController;
import com.aleksey.servlet_app.model.Event;
import com.aleksey.servlet_app.model.EventType;
import com.aleksey.servlet_app.model.File;
import com.aleksey.servlet_app.model.User;
import com.aleksey.servlet_app.repository.EventRepository;
import com.aleksey.servlet_app.repository.FileRepository;
import com.aleksey.servlet_app.repository.UserRepository;
import com.aleksey.servlet_app.repository.hibernate_db.EventRepositoryImpl;
import com.aleksey.servlet_app.repository.hibernate_db.FileRepositoryImpl;
import com.aleksey.servlet_app.repository.hibernate_db.UserRepositoryImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class Main {
    public static void main(String[] args) {

//        UserController userController = new UserController();
//        EventController eventController = new EventController();
//
//        Event event = eventController.getEventById(1);
//        System.out.println(event);

//        List<User> list = userRepository.readAll();
//        list.forEach(u -> System.out.println(u.toString()));

//        File file = new File();
//        file.setFilePath("src/main/file_storage/test.txt");
//        file.setName("test.txt");
//
//        User user = new User();
//        user.setName("second");
//
//        Event event = new Event();
//        event.setUser(user);
//        event.setEventType(EventType.DOWNLOAD);
//        event.setFile(file);
//
//        user.addEvent(event);
//
//        userRepository.create(user);
    }
}