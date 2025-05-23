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
import java.util.List;

public class Events extends HttpServlet {

    private static final long serialVersionUID = 102831973239L;
    private EventController eventController = new EventController();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Event> list = eventController.getAllEvent();
        PrintWriter messageWriter = resp.getWriter();

        for (int i = 0; i < list.size(); i++) {
            String json = objectMapper.writeValueAsString(list.get(i));
            messageWriter.write(json + "\n");
        }
        messageWriter.close();
    }
}
