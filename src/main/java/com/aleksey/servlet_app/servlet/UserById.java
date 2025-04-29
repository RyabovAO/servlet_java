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

public class UserById extends HttpServlet {

    private UserController userController = new UserController();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = req.getPathInfo().replace("/", "");
        User user = userController.getUserById(Integer.parseInt(id));

        PrintWriter messageWriter = resp.getWriter();

        String json = objectMapper.writeValueAsString(user);
        messageWriter.write(json);
    }
}
