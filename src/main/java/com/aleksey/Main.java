package com.aleksey;

import com.aleksey.servlet_app.model.Event;
import com.aleksey.servlet_app.model.EventType;
import com.aleksey.servlet_app.model.File;
import com.aleksey.servlet_app.model.User;
import com.aleksey.servlet_app.repository.EventRepository;
import com.aleksey.servlet_app.repository.UserRepository;
import com.aleksey.servlet_app.repository.hibernate_db.EventRepositoryImpl;
import com.aleksey.servlet_app.repository.hibernate_db.UserRepositoryImpl;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepositoryImpl();

        File file = new File();
        file.setFilePath("src/main/file_storage/test.txt");
        file.setName("test.txt");

        User user = new User();
        user.setName("first");

        Event event = new Event();
        event.setUser(user);
        event.setEventType(EventType.DOWNLOAD);
        event.setFile(file);

        user.addEvent(event);

        userRepository.create(user);
    }
}