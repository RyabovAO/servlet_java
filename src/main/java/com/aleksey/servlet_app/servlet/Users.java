package com.aleksey.servlet_app.servlet;

import com.aleksey.servlet_app.controller.UserController;
import com.aleksey.servlet_app.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class Users extends HttpServlet {

    private static final long serialVersionUID = 102331973239L;
    private UserController userController = new UserController();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<User> list = userController.getAllUser();
        PrintWriter messageWriter = resp.getWriter();

        for (int i = 0; i < list.size(); i++) {
            String json = objectMapper.writeValueAsString(list.get(i));
            messageWriter.write(json + "\n");
        }
        messageWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Scanner scanner = new Scanner(req.getInputStream(), "UTF-8");
        String jsonData = scanner.useDelimiter("\\A").next();
        PrintWriter writer = resp.getWriter();

        User user = objectMapper.readValue(jsonData, User.class);
        userController.createUser(user);

        String json = new ObjectMapper().writeValueAsString(user);
        writer.write(json);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Scanner scanner = new Scanner(req.getInputStream(), "UTF-8");
        String jsonData = scanner.useDelimiter("\\A").next();
        PrintWriter writer = resp.getWriter();

        User user = objectMapper.readValue(jsonData, User.class);
        userController.updateUser(user);

        String json = new ObjectMapper().writeValueAsString(user);
        writer.write(json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Scanner scanner = new Scanner(req.getInputStream(), "UTF-8");
        String jsonData = scanner.useDelimiter("\\A").next();
        PrintWriter writer = resp.getWriter();

        User user = objectMapper.readValue(jsonData, User.class);
        userController.deleteUserById(user.getId());

        String json = new ObjectMapper().writeValueAsString(user);
        writer.write(json);
    }
}
