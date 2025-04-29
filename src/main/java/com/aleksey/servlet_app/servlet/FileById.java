package com.aleksey.servlet_app.servlet;

import com.aleksey.servlet_app.controller.FileController;
import com.aleksey.servlet_app.model.File;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FileById extends HttpServlet {

    private FileController fileController = new FileController();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getPathInfo().replace("/", "");
        File file = fileController.getFileById(Integer.parseInt(id));

        PrintWriter messageWriter = resp.getWriter();

        String json = objectMapper.writeValueAsString(file);
        messageWriter.write(json);
    }
}
