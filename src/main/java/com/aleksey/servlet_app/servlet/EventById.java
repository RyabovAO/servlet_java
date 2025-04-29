package com.aleksey.servlet_app.servlet;

import com.aleksey.servlet_app.controller.EventController;
import com.aleksey.servlet_app.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class EventById extends HttpServlet {

    private EventController eventController = new EventController();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getPathInfo().replace("/", "");
        Event event = eventController.getEventById(Integer.parseInt(id));

        PrintWriter messageWriter = resp.getWriter();

        String json = objectMapper.writeValueAsString(event);
        messageWriter.write(json);
    }
}
