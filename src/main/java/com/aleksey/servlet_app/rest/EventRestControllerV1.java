package com.aleksey.servlet_app.rest;

import com.aleksey.servlet_app.entity.EventEntity;
import com.aleksey.servlet_app.services.EventService;
import com.aleksey.servlet_app.utils.HttpUtils;
import com.aleksey.servlet_app.utils.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class EventRestControllerV1 extends HttpServlet {

    private static final long serialVersionUID = 102831973239L;
    private final EventService eventService = new EventService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter messageWriter = resp.getWriter();
        String json = "";
        String id = HttpUtils.getIdFromRequest(req);

        if(id == null || id.isEmpty()) {
            List<EventEntity> list = eventService.getAllEvent();
            for (int i = 0; i < list.size(); i++) {
                json = JsonUtils.writeJsonAsString(list.get(i));
                messageWriter.write(json + "\n");
            }
        } else {
            EventEntity event = eventService.getEventById(Integer.parseInt(id));
            json = JsonUtils.writeJsonAsString(event);
            messageWriter.write(json);
        }
        messageWriter.close();
    }
}
