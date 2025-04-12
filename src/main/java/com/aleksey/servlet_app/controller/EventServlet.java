package com.aleksey.servlet_app.controller;

import com.aleksey.servlet_app.model.Event;
import com.aleksey.servlet_app.model.User;
import com.aleksey.servlet_app.repository.EventRepository;
import com.aleksey.servlet_app.repository.UserRepository;
import com.aleksey.servlet_app.repository.hibernate_db.EventRepositoryImpl;
import com.aleksey.servlet_app.repository.hibernate_db.UserRepositoryImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class EventServlet extends HttpServlet {

    private static final long serialVersionUID = 102831973239L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EventRepository eventRepository = new EventRepositoryImpl();
        List<Event> list = eventRepository.readAll();

        resp.setContentType("text/html");

        PrintWriter messageWriter = resp.getWriter();

        messageWriter.println("<h1>" + "Download event history" + "<h1>");
        for (int i = 0; i < list.size(); i++) {
            messageWriter.println("<h2>" + list.get(i) + "<h2>");
        }

        messageWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
