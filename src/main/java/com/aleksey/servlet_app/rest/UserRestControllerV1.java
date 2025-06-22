package com.aleksey.servlet_app.rest;

import com.aleksey.servlet_app.entity.UserEntity;
import com.aleksey.servlet_app.services.UserService;
import com.aleksey.servlet_app.utils.HttpUtils;
import com.aleksey.servlet_app.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UserRestControllerV1 extends HttpServlet {

    private static final long serialVersionUID = 102331973239L;
    private final UserService userService = new UserService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter messageWriter = resp.getWriter();
        String json = "";
        String id = HttpUtils.getIdFromRequest(req);

        if(id == null || id.isEmpty()) {
            List<UserEntity> list = userService.getAllUser();
            for (int i = 0; i < list.size(); i++) {
                json = JsonUtils.writeJsonAsString(list.get(i));
                messageWriter.write(json + "\n");
            }
        } else {
            UserEntity user = userService.getUserById(Integer.parseInt(id));
            json = JsonUtils.writeJsonAsString(user);
            messageWriter.write(json);
        }
        messageWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String jsonData = HttpUtils.getBodyFromRequest(req);
        PrintWriter writer = resp.getWriter();

        UserEntity user = objectMapper.readValue(jsonData, UserEntity.class);
        user = userService.createUser(user);

        String json = JsonUtils.writeJsonAsString(user);
        writer.write(json);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String jsonData = HttpUtils.getBodyFromRequest(req);
        PrintWriter writer = resp.getWriter();

        UserEntity user = objectMapper.readValue(jsonData, UserEntity.class);
        userService.updateUser(user);

        String json = JsonUtils.writeJsonAsString(user);
        writer.write(json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String id = HttpUtils.getIdFromRequest(req);
        PrintWriter writer = resp.getWriter();

        UserEntity user = userService.getUserById(Integer.parseInt(id));
        userService.deleteUserById(user.getId());

        String json = JsonUtils.writeJsonAsString(user);
        writer.write(json);
    }
}
